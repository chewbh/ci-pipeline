import hudson.plugins.gradle.GradleInstallation;
import hudson.tools.InstallSourceProperty;
import hudson.tools.ToolProperty;
import hudson.tools.ToolPropertyDescriptor
import hudson.tools.ZipExtractionInstaller;
import hudson.util.DescribableList
import jenkins.model.Jenkins;

def extensions = Jenkins.instance.getExtensionList(GradleInstallation.DescriptorImpl.class)[0]
List<GradleInstallation> installations = []

def installers = Arrays.asList(new File('/downloads')
    .listFiles())
	.findAll {it.toString().startsWith('/downloads/gradle')}.collect {
      def version = it.toString() - '/downloads/gradle-' - '-all.zip' - '-bin.zip'
      def tool = ['name': "$version", 'url': "file:${it.toString()}", 'subdir': "gradle-$version"]
      println("Setting up tool: ${tool.name}, ${tool.url}, ${tool.subdir} ")

      [
        'name': tool.name,
       	'installer': new ZipExtractionInstaller(tool.label as String, tool.url as String, tool.subdir as String)
      ]
    }

installers.each {
	def describableList = new DescribableList<ToolProperty<?>, ToolPropertyDescriptor>()
  	describableList.add(new InstallSourceProperty([it.installer]))
	installations.add(new GradleInstallation(it.name as String, "", describableList))
}

extensions.setInstallations(installations.toArray(new GradleInstallation[installations.size()]))
extensions.save()