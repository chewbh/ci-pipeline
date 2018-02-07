import hudson.tasks.Maven
import hudson.tasks.Maven.MavenInstallation;
import hudson.tools.InstallSourceProperty;
import hudson.tools.ToolProperty;
import hudson.tools.ToolPropertyDescriptor
import hudson.tools.ZipExtractionInstaller;
import hudson.util.DescribableList
import jenkins.model.Jenkins;

def extensions = Jenkins.instance.getExtensionList(Maven.DescriptorImpl.class)[0]
List<MavenInstallation> installations = []

def installers = Arrays.asList(new File('/downloads')
    .listFiles())
	.findAll {it.toString().startsWith('/downloads/apache-maven')}.collect {
      def version = it.toString() - '/downloads/apache-maven-' - '-bin.tar.gz'
      def majorVersion = version.take(1)
      def mavenTool = ['name': "$version", 'url': "file:${it.toString()}", 'subdir': "apache-maven-$version"]
      println("Setting up tool: ${mavenTool.name}, ${mavenTool.url}, ${mavenTool.subdir} ")

      [
        'name': mavenTool.name as String,
       	'installer': new ZipExtractionInstaller(mavenTool.label as String, mavenTool.url as String, mavenTool.subdir as String)
      ]
    }

installers.each {
	def describableList = new DescribableList<ToolProperty<?>, ToolPropertyDescriptor>()
  	describableList.add(new InstallSourceProperty([it.installer]))
	installations.add(new MavenInstallation(it.name as String, "", describableList))
}

extensions.setInstallations(installations.toArray(new MavenInstallation[installations.size()]))
extensions.save()