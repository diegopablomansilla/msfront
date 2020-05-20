package ar.edu.iua.iw1.ej14;

import static javafx.beans.property.ReadOnlyIntegerProperty.readOnlyIntegerProperty;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller implements Initializable {

	private final Task currentTask = new Task();

	private final ObservableList<Task> tasks = FXCollections.observableArrayList();

	private final HashMap<Integer, Task> tasksMap = new HashMap<>();

	public HashMap<Integer, Task> getTasksMap() {
		return tasksMap;
	}
	
	@FXML
    private GridPane gridPane;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private TableView<Task> tasksTable;

	@FXML
	private TableColumn<Task, String> priorityColumn;

	@FXML
	private TableColumn<Task, String> descriptionColumn;

	@FXML
	private TableColumn<Task, String> progressColumn;

	@FXML
	private ComboBox<String> priorities;

	@FXML
	private TextField taskDescription;

	@FXML
	private Button add;

	@FXML
	private Spinner<Integer> progressSpinner;

	@FXML
	private CheckBox completedCheckBox;

	@FXML
	private Button cancel;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		priorities.getItems().addAll("High", "Medium", "Low");
		progressSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));

		progressSpinner.valueProperty()
				.addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
					if (newValue == 100) {
						completedCheckBox.setSelected(true);
					} else {
						completedCheckBox.setSelected(false);
					}
				});

		ReadOnlyIntegerProperty intProgress = readOnlyIntegerProperty(progressSpinner.valueProperty());
		progressBar.progressProperty().bind(intProgress.divide(100.0));

		priorities.valueProperty().bindBidirectional(currentTask.priorityProperty());
		taskDescription.textProperty().bindBidirectional(currentTask.descriptionProperty());
		progressSpinner.getValueFactory().valueProperty().bindBidirectional(currentTask.progressProperty());

		tasksTable.setItems(tasks);
		priorityColumn.setCellValueFactory(rowData -> rowData.getValue().priorityProperty());
		descriptionColumn.setCellValueFactory(rowData -> rowData.getValue().descriptionProperty());
		progressColumn.setCellValueFactory(rowData -> Bindings.concat(rowData.getValue().progressProperty(), "%"));

		StringBinding addButtonTextBinding = new StringBinding() {
			{
				super.bind(currentTask.idProperty());
			}

			@Override
			protected String computeValue() {
				if (currentTask.getId() == null)
					return "Add";
				else
					return "Update";
			}
		};
		add.textProperty().bind(addButtonTextBinding);
		add.disableProperty().bind(Bindings.greaterThan(3, currentTask.descriptionProperty().length()));
		tasksTable.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Task> observable, Task oldValue, Task newValue) -> {
					setCurrentTask(newValue);
				});
	}

	int lastId = 0;

	@FXML
	private void addButtonClicked(ActionEvent event) {
		if (currentTask.getId() == null) {
			Task t = new Task(++lastId, currentTask.getPriority(), currentTask.getDescription(),
					currentTask.getProgress());
			tasks.add(t);
			tasksMap.put(lastId, t);
		} else {
			Task t = tasksMap.get(currentTask.getId());
			t.setDescription(currentTask.getDescription());
			t.setPriority(currentTask.getPriority());
			t.setProgress(currentTask.getProgress());
		}
		setCurrentTask(null);
		
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		
		Group group = new Group();
				
		
		Scene scene = new Scene(group);
		
		
		Stage stage = new Stage();
		stage.setTitle("XXXXXXXXXX");
		
		stage.setScene(scene);
		
		stage.initOwner(gridPane.getScene().getWindow());
//		stage.setMaximized(true);
		((Stage)group.getScene().getWindow()).close();
		stage.show();
		
	}

	@FXML
	private void cancelButtonClicked(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Are you sure you want to cancel?");
		alert.setTitle("Cancelling Update");
		alert.getButtonTypes().remove(0, 2);
		alert.getButtonTypes().add(0, ButtonType.YES);
		alert.getButtonTypes().add(1, ButtonType.NO);
		Optional<ButtonType> confirmationResponse = alert.showAndWait();
		if (confirmationResponse.get() == ButtonType.YES) {
			setCurrentTask(null);
			tasksTable.getSelectionModel().clearSelection();
		}
	}

	private void setCurrentTask(Task selectedTask) {
		if (selectedTask != null) {
			currentTask.setId(selectedTask.getId());
			currentTask.setPriority(selectedTask.getPriority());
			currentTask.setDescription(selectedTask.getDescription());
			currentTask.setProgress(selectedTask.getProgress());
		} else {
			currentTask.setId(null);
			currentTask.setPriority("");
			currentTask.setDescription("");
			currentTask.setProgress(0);
		}
	}

	void setTasksMap(HashMap<Integer, Task> initialTasksMap) {
		tasksMap.clear();
		tasks.clear();

		if (initialTasksMap.size() > 0) {
			tasksMap.putAll(initialTasksMap);
			tasks.addAll(initialTasksMap.values());
			lastId = tasksMap.keySet().stream().max(Integer::compare).get();
		}

	}

}
