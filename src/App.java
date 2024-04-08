import java.util.List;
import java.util.Stack;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;

public class App extends Application {
    private TextField tfNameField = new TextField();
    private TextField tfPriceField = new TextField();
    private ComboBox<String> typeComboBox = new ComboBox<>();
    private Button btDelete = new Button("Delete");
    private Button btAdd = new Button("Add");
    private Button btDone = new Button("Done");
    private Button btReview = new Button("Review");

    private List<String> expenseInfoList = new Stack<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane initscene = new GridPane();
        initscene.setHgap(5);
        initscene.setVgap(5);
        initscene.add(new Label("Name of expense"), 0, 0);
        initscene.add(tfNameField, 1, 0);
        initscene.add(new Label("Price of expense"), 0, 1);
        initscene.add(tfPriceField, 1, 1);
        initscene.add(new Label("Type of expense"), 0, 2);
        initscene.add(typeComboBox, 1, 2);

        

        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(btAdd, btReview, btDone, btDelete);
        initscene.add(hbox, 1, 3);
        
        GridPane.setHalignment(hbox, HPos.RIGHT);

        initscene.setAlignment(Pos.CENTER);
        tfNameField.setAlignment(Pos.BOTTOM_RIGHT);
        tfPriceField.setAlignment(Pos.BOTTOM_RIGHT);

        Scene inputScene = new Scene(initscene, 800, 600);

        primaryStage.setTitle("Budget Calculator");
        primaryStage.setScene(inputScene);
        primaryStage.show();

        typeComboBox.getItems().addAll("Housing/utilities", "Transportation", "Miscellaneous");

        typeComboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Select type");
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
         
            btAdd.setOnAction(e -> {
            String type = typeComboBox.getValue();
            String name = tfNameField.getText();
            String price = tfPriceField.getText();
            String expenseInfo = "Name: " + name + "\nPrice: " + price + "\nType: " + type + "\n";
            expenseInfoList.add(expenseInfo);
            tfNameField.clear();
            tfPriceField.clear();
            typeComboBox.setValue(null);
        });

        btReview.setOnAction(e -> {
            reviewDisplay();          
        });

        btDone.setOnAction(e -> {
            finalDisplay(primaryStage);
        });

        btDelete.setOnAction(e -> {
            if (!expenseInfoList.isEmpty()) {
                expenseInfoList.remove(expenseInfoList.size() - 1);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No expenses to delete");
                alert.showAndWait();
            }
        });
    }
            
        private void reviewDisplay() {
            StringBuilder AllInfo = new StringBuilder();
            double total = 0;

            for (String expenseInfo : expenseInfoList) {
                AllInfo.append(expenseInfo).append("\n");

                String[] lines = expenseInfo.split("\n");
                String priceString = lines[1].substring(lines[1].indexOf(":") + 2);
                double price = Double.parseDouble(priceString);
                total += price;
            }

            AllInfo.append("Total: ").append(String.format("%.2f", total));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Expense Information");
            alert.setHeaderText(null);
            alert.setContentText(AllInfo.toString());
            alert.getButtonTypes().clear();

            ButtonType backButton = new ButtonType("Add more expenses", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().add(backButton);

            alert.showAndWait();           
        };
        private void finalDisplay(Stage primaryStage) {
            StringBuilder AllInfo = new StringBuilder();
            double total = 0;

            for (String expenseInfo : expenseInfoList) {
                AllInfo.append(expenseInfo).append("\n");

                String[] lines = expenseInfo.split("\n");
                String priceString = lines[1].substring(lines[1].indexOf(":") + 2);
                double price = Double.parseDouble(priceString);
                total += price;
            }

            AllInfo.append("Total: ").append(String.format("%.2f", total));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Expense Information");
            alert.setHeaderText(null);
            alert.setContentText(AllInfo.toString());

            alert.getDialogPane().setPrefSize(1500, 770);
            alert.getButtonTypes().clear();
            ButtonType backButton = new ButtonType("Finished", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().add(backButton);
            primaryStage.close();

            alert.showAndWait();
        }
           
        private void updateDisplay(StackPane updStackPane) {
            // updStackPane.getChildren().clear();
            VBox infobox = new VBox(5);
            for (String expenseInfo : expenseInfoList) {
                Text text = new Text(expenseInfo);
                text.setFill(Color.BLACK);
                text.setStyle("-fx-font-size: 18px;");
                text.setTextAlignment(TextAlignment.LEFT);
                VBox.setMargin(text, new Insets(40, 0, 0, 0));
                
                infobox.getChildren().add(text);
        } 
        updStackPane.getChildren().add(infobox);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}

