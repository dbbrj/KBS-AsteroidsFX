package dk.sdu.cbse.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {



    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ModuleConfig.class)) {
            for (String beanName : ctx.getBeanDefinitionNames()) {
                System.out.println(beanName);
            }
            
            Game game = ctx.getBean(Game.class);
            game.start(window);
            game.render();
        }        

    }

}
