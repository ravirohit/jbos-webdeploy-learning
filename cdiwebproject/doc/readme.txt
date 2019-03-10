// link: https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/7.1/html/development_guide/class_loading_and_modules
Q) if we deploy any project in any server(here it wildfly). then it will go to the folder
	  wildfly-10.1.0.Final\standalone\deployments\
	so if we are deploying two project war file i.e. A.war and B.war so, in the same folder both war file will be deployed 
	and will be completely isolated from one another:
	  wildfly-10.1.0.Final\standalone\deployments\A.war
	  wildfly-10.1.0.Final\standalone\deployments\B.war
	Now there is one jar file(common.jar) which is being used by both the jar file. one solution is that jar will be in both war file and work fine. But my 
	question is what if we don't want to keep the jar file in both the war file instead keep it in the deployment folder itself as below:
	  wildfly-10.1.0.Final\standalone\deployments\A.war
	  wildfly-10.1.0.Final\standalone\deployments\B.war
	  wildfly-10.1.0.Final\standalone\deployments\common.jar
	So, Here question is, can A.war and B.war can access the common.jar file. if not, how can we make it possible.
Ans: Wildfly server works based on module structure. So whatever application we deployed on it, it create a isolated module with separate class loader.
    so, for our scenarios, There would be three deployment module in the wildfly with name:
    1. deployment.A.war 2. deployment.B.war 3. deployment.common.jar
    -> As, we know, whichever module of Wildfly we want to associate with our deployed application, we can include or exclude it using jboss-deployment-structure
      .xml file in this project.
    -> create a jboss-deployment-structure.xml in web-inf of A.war and B.war with the following content:
	    <?xml version="1.0" encoding="UTF-8"?>
		<jboss-deployment-structure xmlns="urn:jboss:deployment-structure:1.2">
		<deployment>
		    <dependencies>
		      <module name="deployment.common.jar" />
		    </dependencies>
		</deployment>
		</jboss-deployment-structure>
  Note: 
    -> for the dynamic module(application we deployed to the server), the name of the module will be always prefix with "deployment." just like:if my app name is 
    "abc.war" module name will be "deployment.abc.war".
    -> we also have to make sure, common.jar is not available to A.war and B.war and also it should be available for proper build of project A and B.
      So, we add them as dependency of A and B with scope "provider". This make sure, common.jar will not be packaged inside of both app war file.
    -> advantage of this approach is, A.war and B.war can communicate between them using common.jar. 
    
Q) what is use module.xml file in the project? location: src/main/resources
Q) How to create ear module and deploy it to the jboss server?
Ans: Creation: 
   creating a ear module is as same as creating the war module. only in the packaging tag of pom.xml change war to ear and then run maven install to download
   all the plugin. maven build should be success then do the changes in pom.xml as per need. take the example as ref : cdiwebear module
   Deployment:
   deployment is fine. do the maven build of the ear module and copy this ear file to deployment folder of wildfly server. now start the wildfly it will work
   fine.
   But if we will try to deploy the ear module from eclipse console it will deployed but ear will not have dependent module in it. So always create a ear file
   and then put it to the wildfly manually.
   Cause of issue: i believe the archetype of the ear module should be different than the web module. yet to figure it out. VVI
   
    