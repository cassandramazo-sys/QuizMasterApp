package ph.edu.dlsu.lbycpob.quizmaster;

import javafx.application.Application;
import javafx.stage.Stage;
import ph.edu.dlsu.lbycpob.quizmaster.controller.QuizController;

/**
 * The main application class for the QuizMaster application.
 * This class extends {@link javafx.application.Application} and serves as the entry point
 * for the JavaFX application. It initializes and starts the {@link QuizController}
 * to manage the quiz flow and UI.
 */
public class QuizMasterApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        QuizController controller = new QuizController();
        controller.start(primaryStage);
    }

    /**
     * The main method that launches the JavaFX application.
     * This is the standard entry point for a Java application.
     *
     * @param args The command line arguments passed to the application. Not directly used by this method,
     * but passed to the {@code launch} method.
     */
    public static void main(String[] args) {
        launch(args);
    }
}