# All-the-Spring-Boot-things

Source code of my talk (see https://sessionize.com/luca-bonetti/)

### Notes for IntelliJ IDEA

Go to <code>File > Settings > Build, Execution, Deployment > Compiler</code> and turn the <code>
Build project automatically</code> flag on.

Go to <code>File > Settings > Advanced Settings</code> and under <code>Compiler</code> turn
the <code>Allow auto-make to start even if developed application is currently running</code> flag
on.

You will now be able to automatically make your app restart after each modification without having
to manually restart the whole thing.

Open the <code>/src/main/java/it/lbsoftware/demo/DemoApplication.java</code> file and click on the
green play button that appears on the left of the class name, then choose <code>Run 'DemoApplication.main()'</code> to
start the app.

Note: to correctly run the AI model llama3.2, run <code>docker exec -it <ollama-container-name> ollama pull
llama3.2</code> after the application has started to make the ollama container pull it before reaching the endpoint.
Locally, <code><ollama-container-name></code> is something like <code>all-the-spring-boot-things-ollama-1</code>.