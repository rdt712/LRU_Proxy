Team #1 - LRUProxy README

1.) Install JDK
	-Go to the following link: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html to download Java SE Development Kit 8. 
	-Download the version that is associated with your machine. For example, with Windows 7, you would download the version titled “Windows x64.” Save it somewhere where you will be able to find it later. 
	-Run the .exe file in order to install the JDK onto your system. Keep all of the default options for the install.
	-Edit your environment variables to include your jdk directory. 
		-Go to control panel -> system and security -> system -> advanced system settings -> environment variables.
		-Add 1 new user variable: JAVA_HOME, and set it to the path of your jdk (example: C:\Program Files\Java\jdk1.8.0_60)
		-Click OK, click Apply, and exit out of the control panel.

2.) Install Eclipse Mars
	-Go to the following link: http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/mars/1/eclipse-java-mars-1-win32-x86_64.zip to download Eclipse Mars for Windows x64 machines.
	-Run the .exe file in order to install Eclipse Mars onto your system. Keep all of the default options for the install.
	-When you run Eclipse, it will ask you to select a location for your workspace. Select wherever you wish, but make sure this is a location that you remember and can access easily.

3.) Install Ant
	-Go to the following link: http://ant.apache.org/bindownload.cgi to download Ant. Download the .zip archive with version 1.9.6 of Ant.
	-Unzip the zip distribution file to a local directory (the Ant directory).
	-Edit your environment variables to include the bin folder of the Ant directory. 
		-Go to control panel -> system and security -> system -> advanced system settings -> environment variables. 
		-Add 2 new user variables; ANT_HOME and PATH
		-Set ANT_HOME to the path to your ant install (example: C:\Program Files\apache-ant-1.9.6)
		-Set PATH to the path to your ant bin folder (example: C:\Program Files\apache-ant-1.9.6\bin)
		-Click OK, click Apply, and exit out of the control panel.
	-Ant should have its own ant_junit.jar, but should you encounter an error copy the file junit.jar to the lib folder of the Ant directory. Ant will add the JAR file to the class path for your build.

4.) Download GitHub for Windows
	-Go to the following link: https://desktop.github.com/ and download git for Windows 7 (or later).
	-Run the installer with the recommended settings.
	-Edit your environment variables to include the bin and cmd folders of the git directory.
		-Go to control panel -> system and security -> system -> advanced system settings -> environment variables.
		-Edit the user variable PATH that you created in the last section and add 2 new paths after the first one. (Seperate all paths with a semicolon like so: <PATH>;<PATH>;<PATH> )
		-The two paths should look like "C:\Users\<YOUR USERNAME>\AppData\Local\GitHub\PortableGit_<LONG SEQUENCE OF NUMBERS\LETTERS>\bin" and
			"C:\Users\<YOUR USERNAME>\AppData\Local\GitHub\PortableGit_<LONG SEQUENCE OF NUMBERS\LETTERS>\cmd" respectively.
		-Click OK, click Apply, and exit out of the control panel.

5.) Cloning the Repository in Eclipse
	-Right click in your Package Explorer and select “Import”.
	-Git is automatically installed with Eclipse Mars. Click the arrow next to Git and select “Projects From Git.” Click the next button.
	-Select “Clone URI” and click the next button.
	-In the field titled “URI” in the location box, paste in the follow URI: https://github.com/rdt712/LRU_Proxy.git. Click the next button.
	-Leave the master branch selected on the next screen and hit the next button.
	-Choose the location that you want the repository to be saved to on your machine. You can leave this as the default or choose a new location.
	-Under Wizard for project import, select “Import as general project” and click the next button.
	-Click the finish button and the project will be imported into Eclipse.

6.) Add External Libraries
	-Go to the following link: http://www.java2s.com/Code/Jar/h/Downloadhttpclient411jar.htm to download the httpclient JAR file that you will need for the project.
	-Go to the following link: http://www.java2s.com/Code/Jar/o/Downloadorgapachecommonslogging111jar.htm to download the commons-logging JAR file you will need for the project.
	-Go to the following link: http://www.java2s.com/Code/Jar/h/Downloadhttpcore41jar.htm to download the http-core JAR file you will need for the project.
	-Go to the following link: http://www.java2s.com/Code/Jar/j/Downloadjunit49b2jar.htm to download the JUnit JAR file that you will need for the project.
	-Save all of these JAR files in a location where you can find them easily.
	-Right click on the project that you have imported in Package Explorer and click on "Properties."
	-Click on "Java Build Path" and click on the "Libraries" tab on the top.
	-Click on the "Add External JARs" button on the right.
	-Locate the JAR files that you downloaded in the previous steps, select the JARs, and click the open button.
	-Click the apply button and then the OK button to import the external JARs into the project.
	
7.) Running Ant
	-To run the ANT build file on the project, navigate File Explorer to the LRU_Proxy directory.
	-Shift + Right Click in the directory and select "Open command window here"
	-Run "ant report" in this terminal (or any other target such as "compile", "test", "checkout.repository", etc.)
	-You can also redirect the terminal output into a .txt file by running "ant report -> ant_report.txt"