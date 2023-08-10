package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa05.model.BujoJson;
import cs3500.pa05.model.BujoOptions;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.FileUtils;
import cs3500.pa05.model.JsonUtils;
import cs3500.pa05.model.PwdHash;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Thresholds;
import java.io.File;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * The controller for the journal application
 */
public class JournalControllerImpl implements JournalController {
  // Progress bars
  @FXML
  private ProgressBar sundayProgressBar;
  @FXML
  private ProgressBar mondayProgressBar;
  @FXML
  private ProgressBar tuesdayProgressBar;
  @FXML
  private ProgressBar wednesdayProgressBar;
  @FXML
  private ProgressBar thursdayProgressBar;
  @FXML
  private ProgressBar fridayProgressBar;
  @FXML
  private ProgressBar saturdayProgressBar;

  // Remaining tasks
  @FXML
  private Text sundayTasksRemaining;
  @FXML
  private Text saturdayTasksRemaining;
  @FXML
  private Text mondayTasksRemaining;
  @FXML
  private Text tuesdayTasksRemaining;
  @FXML
  private Text wednesdayTasksRemaining;
  @FXML
  private Text thursdayTasksRemaining;
  @FXML
  private Text fridayTasksRemaining;

  // Overview text
  @FXML
  private Text weeklyOverview;

  // Setting the title
  @FXML
  private Button setTitle;

  // Buttons to add tasks for each day
  @FXML
  private Button sundayAdd;
  @FXML
  private Button mondayAdd;
  @FXML
  private Button tuesdayAdd;
  @FXML
  private Button wednesdayAdd;
  @FXML
  private Button thursdayAdd;
  @FXML
  private Button fridayAdd;
  @FXML
  private Button saturdayAdd;

  // Create task and event buttons
  @FXML
  private Button createTask;
  @FXML
  private Button createEvent;

  // All texts for each day of the week
  @FXML
  private TextArea sundayArea;
  @FXML
  private TextArea mondayArea;
  @FXML
  private TextArea tuesdayArea;
  @FXML
  private TextArea wednesdayArea;
  @FXML
  private TextArea thursdayArea;
  @FXML
  private TextArea fridayArea;
  @FXML
  private TextArea saturdayArea;
  @FXML
  private TextArea quotesAndNotes;
  @FXML
  private TextArea taskQueueArea;
  @FXML
  private Button maxEvent;
  @FXML
  private Button maxTask;
  @FXML
  private Button taskComplete;
  @FXML
  private Button openFile;
  @FXML
  private Button saveFile;
  @FXML
  private Button startMonday;
  @FXML
  private Button startSunday;
  @FXML
  private Button startTuesday;
  @FXML
  private Button startWednesday;
  @FXML
  private Button startThursday;
  @FXML
  private Button startFriday;
  @FXML
  private Button startSaturday;
  @FXML
  private Button openTemplate;
  @FXML
  private Button changePassword;
  @FXML
  private HBox week;
  @FXML
  private VBox taskQueueVbox;
  @FXML
  private VBox sunday;
  @FXML
  private VBox monday;
  @FXML
  private VBox tuesday;
  @FXML
  private VBox wednesday;
  @FXML
  private VBox thursday;
  @FXML
  private VBox friday;
  @FXML
  private VBox saturday;
  @FXML
  private Line sundayLine;
  @FXML
  private Line mondayLine;
  @FXML
  private Line tuesdayLine;
  @FXML
  private Line wednesdayLine;
  @FXML
  private Line thursdayLine;
  @FXML
  private Line fridayLine;
  @FXML
  private Line saturdayLine;
  @FXML
  private TextField typeTitle;
  private TextField eventName;
  private TextField eventDescription;
  private TextField startTime;
  private TextField duration;
  private TextField textFieldForTasksOrEvents;
  private TextField changePasswordText;
  private TextField taskSearch;
  private final Button submitDay;
  private final Button submitTask;
  private final Button submitPasswordChange;
  private final Button closeSearch;
  private final TextArea ta = new TextArea();
  private final TextArea dayOfTask = new TextArea();
  private Popup popup;
  private Popup maxEventsOrTasksPopup;
  private Popup maxEventsOrTasksWarningPopup;
  private final Popup searchPopup = new Popup();
  private final Popup passwordPopup = new Popup();

  private final Stage primaryStage;
  private BujoOptions taskOrEvent;
  private HashMap<String, String> listOfTasks;
  private final List<Day> days;
  private final HashMap<Day, Integer> currentEvents;
  private final HashMap<Day, Integer> currentTasks;
  private int maxEvents;
  private int maxTasks;
  private List<Task> allTasks = new ArrayList<>();
  private List<Event> allEvents = new ArrayList<>();
  private List<TextArea> allDayTextAreas = new ArrayList<>();
  private String title = "";
  private String pwd = "";
  private boolean passed = false;
  private BujoJson bujoJson;
  ///////////////////////////////////////////
  private final KeyCombination newEvent =
      new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
  private final KeyCombination newTask =
      new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
  private final KeyCombination save =
      new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
  private final KeyCombination open =
      new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
  private final KeyCombination newWeek =
      new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
  private final KeyCombination completeTask =
      new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN);
  private final KeyCombination searchForNewTask =
      new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
  //////////////////////////////////////////

  /**
   * Controller Constructor
   *
   * @param primaryStage Stage for our GUI
   */
  public JournalControllerImpl(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setResizable(false);
    this.primaryStage.centerOnScreen();
    eventName = new TextField();
    eventName.setPromptText("Name");
    eventDescription = new TextField();
    eventDescription.setPromptText("Description");
    startTime = new TextField();
    startTime.setPromptText("Start Time");
    duration = new TextField();
    duration.setPromptText("Duration");
    typeTitle = new TextField();
    typeTitle.setPromptText("Set the Name of your Title");
    textFieldForTasksOrEvents = new TextField();
    this.textFieldForTasksOrEvents = new TextField();
    this.taskSearch = new TextField();
    this.maxEvents = 0;
    this.maxTasks = 0;
    this.currentEvents = new HashMap<>();
    this.currentTasks = new HashMap<>();
    this.submitDay = new Button("Submit");
    this.days = new ArrayList<>();
    this.submitTask = new Button("Submit");
    this.closeSearch = new Button("Cancel");
    this.submitPasswordChange = new Button("Submit");
    this.listOfTasks = new HashMap<>();
    this.changePasswordText = new TextField();
    days.add(Day.SUNDAY);
    days.add(Day.MONDAY);
    days.add(Day.TUESDAY);
    days.add(Day.WEDNESDAY);
    days.add(Day.THURSDAY);
    days.add(Day.FRIDAY);
    days.add(Day.SATURDAY);
    this.taskOrEvent = BujoOptions.NONE;
  }

  /**
   * Run method for all the program's duties
   */
  @Override
  @FXML
  public void run() {
    // Loads initial BUJO data
    setTextAreaConditions();
    setConditionsMaxTasks(currentEvents);
    setConditionsMaxTasks(currentTasks);
    popup = new Popup();
    popup.setAutoHide(false);
    maxEventsOrTasksPopup = new Popup();
    maxEventsOrTasksPopup.setAutoHide(true);

    // Resets the text fields
    resetTextFields();
    TextArea warning = new TextArea("CAUTION, EXCEEDED MAX EVENTS OR TASKS");
    warning.setEditable(false);
    Font font = new Font(20);
    warning.setFont(font);
    warning.setStyle("-fx-text-fill: red;");
    warning.setMaxSize(440, 25);
    maxEventsOrTasksWarningPopup = new Popup();
    maxEventsOrTasksWarningPopup.getContent().add(warning);
    maxEventsOrTasksWarningPopup.setAutoHide(true);
    setList();
    setButtonTasks();
    this.sundayProgressBar.setMinSize(165, 15);
  }

  /**
   * Sets all the lists
   */
  private void setList() {
    allDayTextAreas.add(sundayArea);
    allDayTextAreas.add(mondayArea);
    allDayTextAreas.add(tuesdayArea);
    allDayTextAreas.add(wednesdayArea);
    allDayTextAreas.add(thursdayArea);
    allDayTextAreas.add(fridayArea);
    allDayTextAreas.add(saturdayArea);
  }

  /**
   * Set conditions using the bujo data
   *
   * @param bujo BujoJson data
   */
  private void setConditions(BujoJson  bujo) {
    this.taskQueueArea.clear();
    this.pwd = bujo.password();
    this.title = bujo.title();
    this.primaryStage.setTitle(title);
    this.allTasks.clear();
    this.allEvents.clear();
    maxEvents = bujo.thresholds().getMaxEvents();
    maxTasks = bujo.thresholds().getMaxTasks();
    List<Task> tempTasks;
    List<Event> tempEvents;
    for (int i = 0; i < Day.values().length; i++) {
      int finalI = i;
      tempTasks =
          bujo.tasks().stream().filter(t -> t.day() == Day.values()[finalI]).toList();
      tempEvents =
          bujo.events().stream().filter(e -> e.day() == Day.values()[finalI]).toList();
      this.setTextBoxes(Day.values()[finalI], tempTasks, tempEvents);
      for (Task t : tempTasks) {
        if (t.description().isEmpty()) {
          this.allTasks.add(new Task(t.name(), t.day(), t.complete()));
        } else {
          this.allTasks.add(new Task(t.name(), t.description(), t.day(), t.complete()));
        }
      }
      for (Event e : tempEvents) {
        if (e.description().isEmpty()) {
          this.allEvents.add(new Event(e.name(), e.day(), e.time(), e.duration()));
        } else {
          this.allEvents.add(new Event(e.name(), e.description(), e.day(), e.time(), e.duration()));
        }
      }
    }
    if (maxTasks != 0) {
      setAllProgressBars();
    }
    System.out.println(allTasks);
    this.quotesAndNotes.setText(bujo.quotesAndNotes());
  }

  /**
   * Set conditions using the bujo data
   *
   * @param bujo BujoJson data
   */
  private void setConditionsTemplate(BujoJson bujo) {
    this.pwd = "";
    this.allTasks.clear();
    this.allEvents.clear();
    this.taskQueueArea.clear();
    this.title = "";
    primaryStage.setTitle("Your BUJO Journal");
    for (TextArea ta : allDayTextAreas) {
      ta.clear();
    }
    setConditionsMaxTasks(currentEvents);
    setConditionsMaxTasks(currentTasks);
    maxEvents = bujo.thresholds().getMaxEvents();
    maxTasks = bujo.thresholds().getMaxTasks();
    Popup promptSetNewTitle = new Popup();
    TextArea warning = new TextArea("Please set a new Title");
    warning.setEditable(false);
    warning.setMaxSize(440, 25);
    promptSetNewTitle.getContent().add(warning);
    promptSetNewTitle.setAutoHide(true);
    promptSetNewTitle.show(primaryStage);
  }

  /**
   * Sets all the text fields with their prompts
   *
   * @param day    Day of the week
   * @param tasks  List of tasks
   * @param events List of events
   */
  private void setTextBoxes(Day day, List<Task> tasks, List<Event> events) {
    List<String> infoToPutInTextArea = new ArrayList<>();
    for (Task task : tasks) {
      infoToPutInTextArea.add("Task");
      taskQueueArea.appendText("Task" + System.lineSeparator());
      infoToPutInTextArea.add("Name: " + task.name());
      taskQueueArea.appendText("Name: " + task.name() + System.lineSeparator());
      if (!task.description().isEmpty()) {
        infoToPutInTextArea.add("Description: " + task.description());
      }
      if (task.complete()) {
        infoToPutInTextArea.add("Completed!");
        taskQueueArea.appendText("Completed!" + System.lineSeparator() + System.lineSeparator());
      } else {
        infoToPutInTextArea.add("Not Completed");
        taskQueueArea.appendText("Not Completed" + System.lineSeparator() + System.lineSeparator());
      }
      int tempint = currentTasks.get(day) + 1;
      currentTasks.put(day, tempint);
    }
    for (Event event : events) {
      infoToPutInTextArea.add("Event");
      infoToPutInTextArea.add("Name: " + event.name());
      if (!event.description().isEmpty()) {
        infoToPutInTextArea.add("Description: " + event.description());
      }
      infoToPutInTextArea.add("Start Time: " + event.time());
      infoToPutInTextArea.add("Duration: " + event.duration());
      int tempint = currentEvents.get(day) + 1;
      currentEvents.put(day, tempint);
    }
    StringBuilder tempString = new StringBuilder();
    for (String s : infoToPutInTextArea) {
      if (s.equals("Task") || s.equals("Event")) {
        tempString.append(System.lineSeparator());
      }
      tempString.append(s).append(System.lineSeparator());
    }
    TextArea tempArea = giveTextBox(day);
    tempArea.setText(tempString.toString());
  }

  /**
   * Chooses which TextArea of the day of the week to delegate
   *
   * @param day Day of the week
   * @return TextArea with the corresponding day
   */
  private TextArea giveTextBox(Day day) {
    switch (day) {
      case SUNDAY -> {
        return sundayArea;
      }
      case MONDAY -> {
        return mondayArea;
      }
      case TUESDAY -> {
        return tuesdayArea;
      }
      case WEDNESDAY -> {
        return wednesdayArea;
      }
      case THURSDAY -> {
        return thursdayArea;
      }
      case FRIDAY -> {
        return fridayArea;
      }
      default -> {
        return saturdayArea;
      }
    }
  }

  /**
   * Sets everything to 0 in a HashMap before a file is loaded
   *
   * @param max The current events or tasks
   */
  private void setConditionsMaxTasks(HashMap<Day, Integer> max) {
    for (Day d : days) {
      max.put(d, 0);
    }
  }

  /**
   * Sets the initial conditions of the TextArea objects
   */
  private void setTextAreaConditions() {
    sundayArea.setEditable(true);
    sundayArea.setWrapText(true);
    sundayArea.setStyle("-fx-text-fill: #966060");
    mondayArea.setEditable(true);
    mondayArea.setWrapText(true);
    mondayArea.setStyle("-fx-text-fill: #c90000");
    tuesdayArea.setEditable(true);
    tuesdayArea.setWrapText(true);
    tuesdayArea.setStyle("-fx-text-fill: #d1af00");
    wednesdayArea.setEditable(true);
    wednesdayArea.setWrapText(true);
    wednesdayArea.setStyle("-fx-text-fill: #4cbd00");
    thursdayArea.setEditable(true);
    thursdayArea.setWrapText(true);
    thursdayArea.setStyle("-fx-text-fill: #002bab");
    fridayArea.setEditable(true);
    fridayArea.setWrapText(true);
    fridayArea.setStyle("-fx-text-fill: #9c00a3");
    saturdayArea.setEditable(true);
    saturdayArea.setWrapText(true);
    saturdayArea.setStyle("-fx-text-fill: #c44d82");
    quotesAndNotes.setEditable(true);
    quotesAndNotes.setPromptText("You're Quotes and Notes Section!");
  }

  /**
   * Resets the TextFields
   */
  private void resetTextFields() {
    eventName = new TextField();
    eventName.setPromptText("Name");
    eventDescription = new TextField();
    eventDescription.setPromptText("Description");
    startTime = new TextField();
    startTime.setPromptText("Start Time");
    duration = new TextField();
    duration.setPromptText("Duration");
    textFieldForTasksOrEvents = new TextField();
    // May be needed later, but not required on initial query
    //complete = new TextField("Complete: ");
    eventName.setMinWidth(500);
  }

  /**
   * Delegates key events to a key handler
   *
   * @param code The key event such as a key or combination
   */
  @Override
  @FXML
  public void handleInteraction(KeyEvent code) {
    onKeyEvent(code);
  }

  /**
   * Delegates the key events to appropriate reponses
   *
   * @param k KeyEvent
   */
  public void onKeyEvent(KeyEvent k) {
    if (newEvent.match(k)) {
      createEvent.fire();
    } else if (newTask.match(k)) {
      createTask.fire();
    } else if (save.match(k)) {
      saveFile.fire();
    } else if (open.match(k)) {
      openFile.fire();
    } else if (newWeek.match(k)) {
      createNewWeek();
    } else if (completeTask.match(k)) {
      taskComplete.fire();
    } else if (searchForNewTask.match(k)) {
      searchForTask();
    }
  }

  /**
   * Changes the orientation of a week to Horizontal or Vertical
   */
  private void searchForTask() {
    searchPopup.setAutoHide(false);
    taskSearch.setPromptText("Name of Task you want to search for");
    VBox vbox = new VBox(taskSearch, closeSearch);
    searchPopup.getContent().add(vbox);
    searchPopup.show(primaryStage);
  }


  /**
   * Create a new week
   */
  private void createNewWeek() {
    resetTextFields();
    sundayArea.clear();
    mondayArea.clear();
    tuesdayArea.clear();
    wednesdayArea.clear();
    thursdayArea.clear();
    fridayArea.clear();
    saturdayArea.clear();
    quotesAndNotes.clear();
    run();
    primaryStage.setTitle("Your BUJO Journal");
    resetTasks();
  }

  /**
   * Resets the tasks
   */
  private void resetTasks() {
    weeklyOverview.setText("");
    sundayTasksRemaining.setText("");
    mondayTasksRemaining.setText("");
    tuesdayTasksRemaining.setText("");
    wednesdayTasksRemaining.setText("");
    thursdayTasksRemaining.setText("");
    fridayTasksRemaining.setText("");
    saturdayTasksRemaining.setText("");
  }

  /**
   * Sets all progress and tasks for the buttons
   */
  @FXML
  public void setButtonTasks() {
    setWeekButtonTasks(sundayAdd, sundayArea, Day.SUNDAY);
    setWeekButtonTasks(mondayAdd, mondayArea, Day.MONDAY);
    setWeekButtonTasks(tuesdayAdd, tuesdayArea, Day.TUESDAY);
    setWeekButtonTasks(wednesdayAdd, wednesdayArea, Day.WEDNESDAY);
    setWeekButtonTasks(thursdayAdd, thursdayArea, Day.THURSDAY);
    setWeekButtonTasks(fridayAdd, fridayArea, Day.FRIDAY);
    setWeekButtonTasks(saturdayAdd, saturdayArea, Day.SATURDAY);
    createTask.setOnAction(event -> promptNewTask());
    createEvent.setOnAction(event -> promptNewEvent());
    maxTasksAndEvents();
    submitButtons();
    searchTasksButtons();
    fileButtons();
    setTitle.setOnAction(event -> {
      title = typeTitle.getText();
      primaryStage.setTitle(title);
      typeTitle.clear();
    });
    taskComplete.setOnAction(event -> {
      ta.setPromptText("Name of Task you want Complete");
      dayOfTask.setPromptText("Day the Task is in");
      ta.setMaxSize(200, 30);
      dayOfTask.setMaxSize(200, 30);
      Popup markTaskCompletePopup = new Popup();
      markTaskCompletePopup.setAutoHide(true);
      VBox box = new VBox(ta, dayOfTask, submitTask);
      markTaskCompletePopup.getContent().add(box);
      markTaskCompletePopup.show(primaryStage);
    });
    changePassword.setOnAction(event -> {
      changePasswordText.setPromptText("Change You're Password");
      passwordPopup.setAutoHide(true);
      VBox box = new VBox(changePasswordText, submitPasswordChange);
      passwordPopup.getContent().add(box);
      passwordPopup.show(primaryStage);
    });
    setChangeStartDay();
  }

  /**
   * Sets Buttons for everything with files
   */
  private void fileButtons() {
    openFile.setOnAction(event -> {
      Path bujoFile = this.chooseFile();
      BujoJson bujo = JsonUtils.deserializeToBujo(FileUtils.readFile(bujoFile));
      this.bujoJson = bujo;
      this.passed = bujo.password().isBlank();
      if (this.passed) {
        this.setConditions(bujo);
      } else {
        this.pwd = bujo.password();
        promptPassword(false);
      }
    });
    saveFile.setOnAction(event -> {
      saveBujo();
    });
    openTemplate.setOnAction(event -> {
      Path bujoFile = this.chooseFile();
      BujoJson bujo = JsonUtils.deserializeToBujo(FileUtils.readFile(bujoFile));
      this.bujoJson = bujo;
      setConditionsTemplate(bujo);
    });
  }

  /**
   * Sets buttons for searches
   */
  private void searchTasksButtons() {
    taskSearch.setOnKeyReleased(event -> {
      String searchText = taskSearch.getText();
      taskQueueArea.clear();
      List<Task> tempList = new ArrayList<>();
      for (Task t : allTasks) {
        if (t.name().toUpperCase().startsWith(searchText.toUpperCase())) {
          tempList.add(t);
        }
      }
      for (Task t : tempList) {
        formatTask(t, taskQueueArea);
      }
    });
    closeSearch.setOnAction(event -> {
      searchPopup.hide();
      taskSearch.clear();
      taskQueueArea.clear();
      for (Task t : allTasks) {
        formatTask(t, taskQueueArea);
      }
    });
  }

  /**
   * Sets Buttons for submit buttons
   */
  private void submitButtons() {
    submitTask.setOnAction(event -> {
      String task = ta.getText();
      for (Task t :
          allTasks) {
        if (t.name().equalsIgnoreCase(task) && t.day().equals(Day.valueOf(
            dayOfTask.getText().toUpperCase())) && !t.complete()) {
          System.out.println(t.name());
          allTasks.add(new Task(t.name(), t.description(), t.day(), true));
          allTasks.remove(t);
          System.out.println(allTasks);
        }
      }
      updateTextArea();
      updateTaskQueue();
    });
    submitDay.setOnAction(event -> {
      int maxNum = 0;
      try {
        maxNum = Integer.parseInt(textFieldForTasksOrEvents.getText());
      } catch (NumberFormatException e) {
        System.out.println("bad");
      }
      System.out.println(maxNum);
      maxEventsOrTasksPopup.hide();
      if (taskOrEvent.equals(BujoOptions.TASK)) {
        maxTasks = maxNum;
      } else if (taskOrEvent.equals(BujoOptions.EVENT)) {
        maxEvents = maxNum;
      }
      maxEventsOrTasksPopup.hide();
      taskOrEvent = BujoOptions.NONE;
    });
    submitPasswordChange.setOnAction(event -> {
      this.pwd = PwdHash.hashSha256(changePasswordText.getText(), PwdHash.sha256());
      changePasswordText.clear();
      passwordPopup.hide();
      System.out.println(this.pwd);
    });
  }

  /**
   * Sets buttons for max tasks and events
   */
  private void maxTasksAndEvents() {
    maxEvent.setOnAction(event -> {
      popup.hide();
      taskOrEvent = BujoOptions.EVENT;
      textFieldForTasksOrEvents.clear();
      Label maxEventsLabel = new Label("Max Events: ");
      HBox maxEventsBox = new HBox();
      maxEventsBox.getChildren().addAll(maxEventsLabel, textFieldForTasksOrEvents);
      VBox tempVbox = new VBox(maxEventsBox, submitDay);
      maxEventsOrTasksPopup.getContent().clear();
      maxEventsOrTasksPopup.getContent().addAll(tempVbox);
      maxEventsOrTasksPopup.show(primaryStage);
    });
    maxTask.setOnAction(event -> {
      popup.hide();
      taskOrEvent = BujoOptions.TASK;
      textFieldForTasksOrEvents.clear();
      Label maxTasksLabel = new Label("Max Tasks: ");
      HBox maxTasksBox = new HBox();
      maxTasksBox.getChildren().addAll(maxTasksLabel, textFieldForTasksOrEvents);
      VBox tempVbox = new VBox(maxTasksBox, submitDay);
      maxEventsOrTasksPopup.getContent().clear();
      maxEventsOrTasksPopup.getContent().addAll(tempVbox);
      maxEventsOrTasksPopup.show(primaryStage);
    });
  }


  private void updateTextArea() {
    for (TextArea ta : allDayTextAreas) {
      System.out.println("here");
      String nameOfTa = ta.getId();
      int index = nameOfTa.indexOf("Area");
      System.out.println(nameOfTa);
      System.out.println(dayOfTask.getText());
      if (nameOfTa.substring(0, index).equalsIgnoreCase(dayOfTask.getText())) {
        ta.clear();
        for (Task t : allTasks) {
          if (t.day().toString().equalsIgnoreCase(dayOfTask.getText())) {
            formatTask(t, ta);
          }
        }
        for (Event e : allEvents) {
          formatEvent(e, ta);
        }
      }
    }
    ta.clear();
    dayOfTask.clear();
  }

  /**
   * Formats an Event
   *
   * @param e  Event
   * @param ta the Area to put it in
   */
  private void formatEvent(Event e, TextArea ta) {
    if (e.day().toString().equalsIgnoreCase(dayOfTask.getText())) {
      StringBuilder sb = new StringBuilder();
      sb.append(System.lineSeparator()).append("Event").append(System.lineSeparator());
      sb.append("Name: ").append(e.name()).append(System.lineSeparator());
      if (!e.description().isEmpty()) {
        sb.append("Description: ").append(e.description()).append(System.lineSeparator());
      }
      sb.append("Start Time: ").append(e.time()).append(System.lineSeparator());
      sb.append("Duration: ").append(e.duration()).append(System.lineSeparator());
      ta.appendText(sb.toString());
    }
  }

  /**
   * Formats a task
   *
   * @param t        the Task
   * @param taskArea the area the task goes in
   */
  private void formatTask(Task t, TextArea taskArea) {
    StringBuilder sb = new StringBuilder();
    sb.append("Task").append(System.lineSeparator());
    sb.append("Name: ").append(t.name()).append(System.lineSeparator());
    if (!t.description().isEmpty()) {
      sb.append("Description: ").append(t.description()).append(System.lineSeparator());
    }
    if (t.complete()) {
      System.out.println("hi");
      sb.append("Completed!").append(System.lineSeparator()).append(System.lineSeparator());
    } else {
      System.out.println("bye");
      sb.append("Not Completed.").append(System.lineSeparator())
          .append(System.lineSeparator());
    }
    taskArea.appendText(sb.toString());
  }

  /**
   * Changes the starting day for all das
   */
  private void setChangeStartDay() {
    startSunday.setOnAction(event -> {
      changeStartDay(taskQueueVbox, sundayLine, sunday, mondayLine, monday, tuesdayLine,
          tuesday, wednesdayLine, wednesday, thursdayLine, thursday, fridayLine, friday,
          saturdayLine, saturday);
    });
    startMonday.setOnAction(event -> {
      changeStartDay(taskQueueVbox, mondayLine, monday, tuesdayLine,
          tuesday, wednesdayLine, wednesday, thursdayLine, thursday, fridayLine, friday,
          saturdayLine, saturday, sundayLine, sunday);
    });
    startTuesday.setOnAction(event -> {
      changeStartDay(taskQueueVbox, tuesdayLine,
          tuesday, wednesdayLine, wednesday, thursdayLine, thursday, fridayLine, friday,
          saturdayLine, saturday, sundayLine, sunday, mondayLine, monday);
    });
    startWednesday.setOnAction(event -> {
      changeStartDay(taskQueueVbox, wednesdayLine, wednesday,
          thursdayLine, thursday, fridayLine, friday, saturdayLine, saturday, sundayLine,
          sunday, mondayLine, monday, tuesdayLine, tuesday);
    });
    startThursday.setOnAction(event -> {
      changeStartDay(taskQueueVbox, thursdayLine, thursday, fridayLine, friday,
          saturdayLine, saturday, sundayLine, sunday, mondayLine, monday, tuesdayLine,
          tuesday, wednesdayLine, wednesday);
    });
    startFriday.setOnAction(event -> {
      changeStartDay(taskQueueVbox, fridayLine, friday,
          saturdayLine, saturday, sundayLine, sunday, mondayLine, monday, tuesdayLine,
          tuesday, wednesdayLine, wednesday, thursdayLine, thursday);
    });
    startSaturday.setOnAction(event -> {
      changeStartDay(taskQueueVbox, saturdayLine, saturday, sundayLine, sunday,
          mondayLine, monday, tuesdayLine, tuesday, wednesdayLine, wednesday, thursdayLine,
          thursday, fridayLine, friday);
    });
  }

  /**
   * Change start day via VBox
   */
  private void changeStartDay(VBox taskQueueVbox, Line line1, VBox day1, Line line2, VBox day2,
                              Line line3, VBox day3, Line line4, VBox day4, Line line5, VBox day5,
                              Line line6, VBox day6, Line line7, VBox day7) {
    week.getChildren().clear();
    week.getChildren().addAll(taskQueueVbox, line1, day1, line2, day2, line3,
        day3, line4, day4, line5, day5, line6, day6, line7, day7);
  }

  /**
   * Prompts user for new task
   */
  private void promptNewTask() {
    maxEventsOrTasksPopup.hide();
    textFieldForTasksOrEvents.clear();
    taskOrEvent = BujoOptions.TASK;
    resetTextFields();
    VBox tasks1 = new VBox();
    tasks1.getChildren().addAll(eventName, eventDescription);
    popup.getContent().clear();
    popup.getContent().addAll(tasks1);
    popup.show(primaryStage);
  }

  /**
   * Prompts user for new event
   */
  private void promptNewEvent() {
    maxEventsOrTasksPopup.hide();
    textFieldForTasksOrEvents.clear();
    taskOrEvent = BujoOptions.EVENT;
    resetTextFields();
    VBox event1 = new VBox();
    event1.getChildren().addAll(eventName, eventDescription, startTime, duration);
    popup.getContent().clear();
    popup.getContent().addAll(event1);
    popup.show(primaryStage);
  }

  /**
   * Saves the bujo and writes it to the target directory supplied from the initial upload
   */
  private void saveBujo() {
    Thresholds caps = new Thresholds(this.maxEvents, this.maxTasks);
    BujoJson bujo = new BujoJson(title,
        this.allTasks, this.allEvents, caps, quotesAndNotes.getText(), pwd);
    JsonNode json = JsonUtils.serializeRecord(bujo);
    Path path = Path.of(bujo.title() + ".bujo");
    FileUtils.writeToFile(path, json.toString());
  }

  /**
   * If the person makes a task, and presses the creation button, then it sets the data to that day
   *
   * @param weekButton   The week's button
   * @param weekTextArea The week's text area
   * @param dayOfTheWeek The day of the week
   */
  private void setWeekButtonTasks(Button weekButton, TextArea weekTextArea, Day dayOfTheWeek) {
    weekButton.setOnAction(event -> {
      System.out.println("HI");
      String tempString = getStringsFromInput(dayOfTheWeek);
      weekTextArea.appendText(tempString);
      if (taskOrEvent.equals(BujoOptions.TASK)) {
        int currentNumTask = currentTasks.get(dayOfTheWeek);
        currentNumTask++;
        currentTasks.put(dayOfTheWeek, currentNumTask);
        //Progress Bar things
        if (maxTasks != 0) {
          setAllProgressBars();
          formatWeeklyOverview();
        }
      } else if (taskOrEvent.equals(BujoOptions.EVENT)) {
        int currentNumEvent = currentEvents.get(dayOfTheWeek);
        System.out.println(currentNumEvent);
        currentNumEvent++;
        currentEvents.put(dayOfTheWeek, currentNumEvent);
      }
      if ((currentTasks.get(dayOfTheWeek) > maxTasks && maxTasks != 0)
          || (currentEvents.get(dayOfTheWeek) > maxEvents && maxEvents != 0)) {
        maxEventsOrTasksWarningPopup.show(primaryStage);
      }
      updateTaskQueue();
      taskOrEvent = BujoOptions.NONE;
    });
  }

  private void updateTaskQueue() {
    taskQueueArea.clear();
    for (Task t : allTasks) {
      formatTask(t, taskQueueArea);
    }
  }

  /**
   * Sets all progress bars
   */
  private void setAllProgressBars() {
    setProgressBar(Day.MONDAY, mondayProgressBar, mondayTasksRemaining);
    setProgressBar(Day.TUESDAY, tuesdayProgressBar, tuesdayTasksRemaining);
    setProgressBar(Day.WEDNESDAY, wednesdayProgressBar, wednesdayTasksRemaining);
    setProgressBar(Day.THURSDAY, thursdayProgressBar, thursdayTasksRemaining);
    setProgressBar(Day.FRIDAY, fridayProgressBar, fridayTasksRemaining);
    setProgressBar(Day.SATURDAY, saturdayProgressBar, saturdayTasksRemaining);
    setProgressBar(Day.SUNDAY, sundayProgressBar, sundayTasksRemaining);
  }

  private void setProgressBar(Day day, ProgressBar progressBar, Text tasksRemaining) {
    double numTasks = currentTasks.get(day);
    progressBar.setProgress(numTasks / maxTasks);
    tasksRemaining.setText(String.valueOf(maxTasks - numTasks));
  }

  /**
   * Formats the weekly overview
   */
  private void formatWeeklyOverview() {
    StringBuilder sb = new StringBuilder();
    double i = 0;
    double k = 0;
    for (Day d : days) {
      i += currentTasks.get(d);
      k += currentEvents.get(d);
    }
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    sb.append("Total Tasks: ").append(i).append("   ");
    sb.append("Total Events: ").append(k).append("   ");
    String completed = decimalFormat.format((i / ((double) maxTasks * 7)) * 100);
    sb.append("Tasks Completed: ").append(completed).append("%");
    weeklyOverview.setText(sb.toString());
  }

  /**
   * Gets the strings from the user via the day
   *
   * @param day Day of the week
   * @return Input as a string
   */
  private String getStringsFromInput(Day day) {
    StringBuilder listOfInputs = new StringBuilder();
    if (!taskOrEvent.equals(BujoOptions.NONE)) {
      popup.hide();
      if (eventName.getText().isEmpty() || (taskOrEvent.equals(BujoOptions.EVENT)
          && (startTime.getText().isEmpty() || duration.getText().isEmpty()))) {
        createWarningPopup();
      } else {
        listOfInputs = formatInputs();
      }
      listOfInputs.append(System.lineSeparator());
      setAllTasksEvents(day);
      resetTextFields();
    }
    return listOfInputs.toString();
  }

  /**
   * sets all the tasks and events into their respective list
   *
   * @param day the day to mark the task as
   */
  private void setAllTasksEvents(Day day) {
    if (taskOrEvent.getOption().equals("Task")) {
      // save it to a list of tasks
      if (eventDescription.getText().isEmpty()) {
        Task newTask =
            new Task(eventName.getText(), day, false);
        this.allTasks.add(newTask);
      } else {
        Task newTask =
            new Task(eventName.getText(), eventDescription.getText(), day, false);
        this.allTasks.add(newTask);
      }
    }
    if (taskOrEvent.getOption().equals("Event")) {
      //save it to a list of events
      if (eventDescription.getText().isEmpty()) {
        Event newEvent =
            new Event(eventName.getText(), day, startTime.getText(), duration.getText());
        this.allEvents.add(newEvent);
      } else {
        Event newEvent =
            new Event(eventName.getText(),
                eventDescription.getText(), day, startTime.getText(), duration.getText());
        this.allEvents.add(newEvent);
      }
    }
  }

  /**
   * Creates a Popup warning the user that they left a field empty
   */
  private void createWarningPopup() {
    //Create Warning that Fields are empty
    TextArea warning = new TextArea("WARNING, ONE OR MORE FIELD EMPTY");
    warning.setEditable(false);
    Font font = new Font(20);
    warning.setFont(font);
    warning.setStyle("-fx-text-fill: red;");
    warning.setMaxSize(390, 25);
    Popup warningNoText = new Popup();
    warningNoText.getContent().add(warning);
    warningNoText.show(primaryStage);
    warningNoText.setAutoHide(true);
  }

  /**
   * Format the inputs and returns the string builder
   *
   * @return A list of inputs
   */
  private StringBuilder formatInputs() {
    StringBuilder listOfInputs = new StringBuilder();
    // get the inputs
    listOfInputs.append(taskOrEvent.getOption()).append(System.lineSeparator());
    listOfInputs.append("Name: ").append(eventName.getText()).append(System.lineSeparator());
    if (!eventDescription.getText().isEmpty()) {
      listOfInputs.append("Description: ").append(eventDescription.getText())
          .append(System.lineSeparator());
    }
    if (taskOrEvent.equals(BujoOptions.EVENT)) {
      listOfInputs.append("Start Time: ").append(startTime.getText())
          .append(System.lineSeparator());
      listOfInputs.append("Duration: ").append(duration.getText())
          .append(System.lineSeparator());
    } else if (taskOrEvent.equals(BujoOptions.TASK)) {
      listOfInputs.append("Not Completed.");
    }
    listOfTasks.put(eventName.getText(), "Not Completed.");
    listOfInputs.append(System.lineSeparator());
    return listOfInputs;
  }

  /**
   * Prompts the user for a file
   *
   * @return The path to the user's prompted file
   */
  private Path chooseFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Bujo File");
    configureFileChooser(fileChooser);
    File file = fileChooser.showOpenDialog(this.primaryStage);
    if (file == null) {
      Platform.exit();
    }
    assert file != null;
    return file.toPath();
  }

  /**
   * Configures the FileChooser object's extensions
   *
   * @param fileChooser A FileChooser to configure
   */
  private static void configureFileChooser(FileChooser fileChooser) {
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("BUJO", "*.bujo"));
  }

  /**
   * Prompts the user for a password
   *
   * @param promptAgain -  if true, keep prompting them
   */
  private void promptPassword(boolean promptAgain) {
    if (this.pwd.isBlank()) {
      return;
    }
    PasswordField password = new PasswordField();
    password.setPrefColumnCount(20);
    HBox pwdHbox = new HBox(password);
    pwdHbox.setPadding(new Insets(10));
    pwdHbox.setSpacing(10);
    // Password buttons
    Button btnGo = new Button("Go");
    Button btnCancel = new Button("Cancel");
    btnGo.setPrefHeight(25);
    btnCancel.setPrefHeight(25);
    btnCancel.setOnAction(e -> {
      popup.getContent().clear();
      popup.show(primaryStage);
      primaryStage.close();
    });
    btnGo.setOnAction(e -> {
      boolean passed = verifyPwd(password.getText());
      if (passed) {
        this.passed = true;
        this.setConditions(this.bujoJson);
      } else {
        this.passed = false;
        promptPassword(true);
      }
      popup.getContent().clear();
    });
    HBox btnHbox = new HBox(btnGo, btnCancel);
    btnHbox.setSpacing(10);
    btnHbox.setAlignment(Pos.CENTER);
    Label labelPwd = new Label(!promptAgain ? "Password: " : "Please try again: ");
    VBox promptVbox = new VBox(labelPwd, pwdHbox, btnHbox);
    promptVbox.setAlignment(Pos.CENTER);
    BorderPane pwdRootComponent = new BorderPane();
    pwdRootComponent.setTop(promptVbox);
    popup.getContent().clear();
    popup.getContent().addAll(pwdRootComponent);
    popup.show(primaryStage);
  }

  /**
   * Verifies if the attempted password matches
   *
   * @param attemptedPwd Attempted plain-text password
   * @return True if it matches, false if otherwise
   */
  private boolean verifyPwd(String attemptedPwd) {
    return PwdHash.hashSha256(attemptedPwd, PwdHash.sha256()).equals(this.pwd);
  }
}
