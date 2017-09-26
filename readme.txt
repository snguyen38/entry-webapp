Entry Webapp

System required
- Java 8
- Maven 3
- Tomcat 9

Build & deploy
- Build war file with maven comman: mvn clean install -DskipTests
- After build success war file is on target folder with name: entry-webapp.war
- Bring that war to Tomcat webapp folder and start Tomcat server
- Access to Webapp by link: http://localhost:{port}/entry-webapp/
- Login page will be show up
- Login with either added account:
	+ admin:admin
 	+ member:member
- Or you can register your account