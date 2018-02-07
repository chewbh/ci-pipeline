import hudson.model.JDK
import hudson.tools.InstallSourceProperty
import hudson.tools.ZipExtractionInstaller

def descriptor = new JDK.DescriptorImpl();
def List<JDK> installations = []

def installers = Arrays.asList(new File('/downloads')
    .listFiles())
	.findAll {it.toString().startsWith('/downloads/jdk')}.collect {
        def version = it.toString() - '/downloads/jdk-' - '-linux-x64.tar.gz' - '_linux-x64_bin.tar.gz'
        def majorVersion = version.take(1)
      	def subdir = "jdk-$version"
        if(majorVersion.toInteger() <= 8) {
          subdir = "jdk1.${majorVersion}.0_${version.drop(2)}"
        }
        def tool = ['name': "$version", 'url': "file:${it.toString()}", 'subdir': subdir]
        def installer = new ZipExtractionInstaller(tool.label as String, tool.url as String, tool.subdir as String)
        println("Setting up tool: ${tool.name}, ${tool.url}, ${tool.subdir}")

        new JDK(tool.name as String, null, [new InstallSourceProperty([installer])])
    }
    .each { installations.add(it) }

descriptor.setInstallations(installations.toArray(new JDK[installations.size()]))
descriptor.save()