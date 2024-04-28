package org.example.pau3;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.util.converter.DoubleStringConverter;
import org.example.pau3.external.ClassEmployee;
import org.example.pau3.external.Employee;
import org.example.pau3.external.EmployeeCondition;
import org.example.pau3.external.Rate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class RootController
{
    //Attributes
    //data
    ArrayList<ClassEmployee> groups_arr = new ArrayList<>();

    //buttons
    @FXML private Button groupModify;
    @FXML private Button employeeModify;

    //text
    private final String UNLOCK_MODIFY = "Unlock";
    private final String LOCK_MODIFY = "Lock";
    private final String NO_EMPLOYEE_IN_GROUP_TEXT = "Selected group has no assigned personnel";
    private final String NO_EMPLOYEES_FOUND_TEXT = "No employees found";

    //textfields
    @FXML private TextField filterField;

    //labels
    @FXML Label employeeLabel;

    //group table
    @FXML private TableView<ClassEmployee> groupTable;
    @FXML private TableColumn<ClassEmployee, String> workgroupCol;
    @FXML private TableColumn<ClassEmployee, String> staffCol;
    @FXML private TableColumn<ClassEmployee, Integer> slotsCol;
    @FXML private TableColumn<ClassEmployee, String> reviewCol;
    @FXML private TableColumn<ClassEmployee, String> scoreCol;
    ObservableList<ClassEmployee> groupTableList;

    //employee table
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> nameCol;
    @FXML private TableColumn<Employee, String> surnameCol;
    @FXML private TableColumn<Employee, EmployeeCondition> conditionCol;
    @FXML private TableColumn<Employee, Integer> yearCol;
    @FXML private TableColumn<Employee, Double> salaryCol;

    // Review pane
    @FXML private Pane reviewPane;
    @FXML private TextArea commentArea;
    @FXML private ChoiceBox<Integer> scoreBox;
    private int default_rating;

    //active selections
    ClassEmployee groupSelection;
    Employee employeeSelection;

    //Methods
    //initialization
    @FXML public void initialize()
    {
        initializeData();
        initializeGroupTable();
        initializeEmployeeTable();
        initializeListeners();
        initializeVariables();
        initializeReviewPane();
    }

    void initializeVariables()
    {
        groupSelection = null;
        employeeSelection = null;
    }

    private void initializeListeners()
    {
        //TABELA GROUP
        //utworzenie listenera na zmiane wyboru w tabeli dla grup
        groupTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            if (newSelection != null)
            {
                onGroupChange(newSelection);
            }
        });

        //listener zmiany nazwy
        workgroupCol.setOnEditCommit(this::updateGroupName);
        //listener zmiany ilosci miejsc
        slotsCol.setOnEditCommit(this::updateSlot);

        //TABELA EMPLOYEE
        //utworzenie listenera na zmiane osoby w tabeli employee
        employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            if(newSelection != null)
            {
                onEmployeeChange(newSelection);
            }
        });

        //name change listener
        nameCol.setOnEditCommit(this::updateEmployeeName);
        //surname change listener
        surnameCol.setOnEditCommit(this::updateEmployeeSurname);
        //condition change listener
        conditionCol.setOnEditCommit(this::updateEmployeeCondition);
        //birth_year change listener
        yearCol.setOnEditCommit(this::updateEmployeeBirth);
        //salary change listener
        salaryCol.setOnEditCommit(this::updateEmployeeSalary);
    }

    @FXML public void initializeGroupTable()
    {
        //wylacz edytowanie tabeli
        groupTable.setEditable(false);

        //mapowanie kolumny z nazwami
        workgroupCol.setCellValueFactory(new PropertyValueFactory<ClassEmployee, String>("workgroup"));
        workgroupCol.setCellFactory(TextFieldTableCell.forTableColumn());

        //mapowanie max pracownikow
        slotsCol.setCellValueFactory(new PropertyValueFactory<ClassEmployee, Integer>("maxEmployees"));
        slotsCol.setCellFactory(TextFieldTableCell.forTableColumn(new SafeIntegerStringConverter()));

        //mapowanie kolumny z iloscia pracownikow
        staffCol.setCellValueFactory(cellData ->
        {
            ClassEmployee classEmployee = cellData.getValue();
            String utilizationText = String.format("%d", classEmployee.getEmployeeList().size());
            return new SimpleStringProperty(utilizationText);
        });

        // Mapowanie kolumny z ilością recenzji
        reviewCol.setCellValueFactory(cellData -> {
            ClassEmployee classEmployee = cellData.getValue();
            String reviewCountText = String.format("%d", classEmployee.getReviewCount());
            return new SimpleStringProperty(reviewCountText);
        });

        // Mapowanie kolumny ze średnią ocen
        scoreCol.setCellValueFactory(cellData -> {
            ClassEmployee classEmployee = cellData.getValue();
            String averageScoreText = String.format("%.2f", classEmployee.getAverageScore());
            return new SimpleStringProperty(averageScoreText);
        });

        // Ustawienie sposobu wyświetlania danych w komórkach
        reviewCol.setCellFactory(TextFieldTableCell.forTableColumn());
        scoreCol.setCellFactory(TextFieldTableCell.forTableColumn());

        //wstawienie do tabeli danych
        groupTableList = FXCollections.observableArrayList(groups_arr);
        groupTable.setItems(groupTableList);
    }

    @FXML private void initializeEmployeeTable()
    {
        //wylacz edytowanie tabeli
        employeeTable.setEditable(false);

        //disable visibility
        employeeTable.setVisible(false);

        //mapowanie kolumn
        nameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        surnameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
        surnameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        conditionCol.setCellValueFactory(new PropertyValueFactory<Employee, EmployeeCondition>("employeeCondition"));
        conditionCol.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(EmployeeCondition.values())));

        yearCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("birth_year"));
        yearCol.setCellFactory(TextFieldTableCell.forTableColumn(new SafeIntegerStringConverter()));

        salaryCol.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
        salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        //listener to state change for the filter text box
        filterField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            filterList();
        });
    }

    private void initializeReviewPane()
    {
        // Make pane initially invisible
        reviewPane.setVisible(false);

        // Add rating values to choice box
        for(int i = Rate.LOWER_BOUND; i <= Rate.UPPER_BOUND; i++)
            scoreBox.getItems().add(i);

        // Set a default value of choice box as max score
        scoreBox.setValue(Rate.UPPER_BOUND);
        default_rating = Rate.UPPER_BOUND;
    }

    public void initializeData()
    {
        List<ClassEmployee> CE = DatabaseController.loadDB();
        groups_arr.addAll(CE);
    }

    //Listeners for group table
    private void onGroupChange(ClassEmployee newSelection)
    {
        updateEmployeeTable(newSelection.getEmployeeList());
        groupSelection = newSelection;

        //enable lock on right table
        if(employeeTable.isEditable())
            editEmployeeEvent();

        //clear the filter textField
        filterField.setText("");

        // Make the review pane visible
        reviewPane.setVisible(true);

        // Debug printing
        System.out.println(
                "--------------------------------------------------"
                + "\nSelected workgroup: " + newSelection.getWorkgroup()
                + "\nReviews: "
        );

        for(Rate rate : groupSelection.getRatingList())
        {
            System.out.println(
                    "Score: " + rate.getRating()
                    + "\tDate: " + rate.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss"))
                    + "\tComment: " + rate.getComment()
            );
        }
    }

    private void updateGroupName(TableColumn.CellEditEvent<ClassEmployee, String> event)
    {
        int index = event.getTablePosition().getRow();
        String newValue = event.getNewValue();

        if(newValue.isEmpty())
            System.out.println("ILLEGAL: attempted to remove workgroup name for: " + groups_arr.get(index).getWorkgroup());
        else
            groupTableList.get(index).setWorkgroup(newValue);

        groupTable.refresh();

        //zaktualizuj dane w bazie
        updateData(index);
    }

    private void updateSlot(TableColumn.CellEditEvent<ClassEmployee, Integer> event)
    {
        //handle null as 0
        Integer new_val_obj = event.getNewValue();
        int new_val = (new_val_obj != null) ? new_val_obj : 0;

        int index = event.getTablePosition().getRow();

        if(new_val < groups_arr.get(index).getEmployeeList().size())
            System.out.println("ILLEGAL: slot below staff attempted for workgroup: " +  groups_arr.get(index).getWorkgroup());
        else
        {
            groupTableList.get(index).setMax(new_val);
            updateData(index);
        }

        groupTable.refresh();
    }

    //listener for employee table
    private void onEmployeeChange(Employee newSelection)
    {
        System.out.println("Selected employee: " + newSelection.getName() + " " + newSelection.getSurname());
        employeeSelection = newSelection;
    }

    void updateEmployeeTable(List<Employee> employeeList)
    {
        if(employeeList.isEmpty())
        {
            employeeLabel.setText(NO_EMPLOYEE_IN_GROUP_TEXT);
            employeeTable.setVisible(false);
        }
        else
        {
            employeeTable.setItems(FXCollections.observableArrayList(employeeList));
            employeeTable.setVisible(true);
        }
    }

    private void updateEmployeeName(TableColumn.CellEditEvent<Employee, String> event)
    {
        String newName = event.getNewValue();

        if(newName.isEmpty())
            System.out.println("ILLEGAL: attempt at invalid name assignment for an employee");
        else
        {
            //table update
            int groupIndex = groupTableList.indexOf(groupSelection);
            int employeeIndex = groupTableList.get(groupIndex).getEmployeeList().indexOf(employeeSelection);
            groupTableList.get(groupIndex).getEmployeeList().get(employeeIndex).setName(newName);

            //database update
            updateData(groupIndex);
        }

        employeeTable.refresh();

        Platform.runLater(() ->
        {
            event.getTableView().requestFocus();
        });
    }

    private void updateEmployeeSurname(TableColumn.CellEditEvent<Employee, String> event)
    {
        String newSurname = event.getNewValue();

        if(newSurname.isEmpty())
            System.out.println("ILLEGAL: attempt at invalid surname assignment for an employee");
        else
        {
            //table update
            int groupIndex = groupTableList.indexOf(groupSelection);
            int employeeIndex = groupTableList.get(groupIndex).getEmployeeList().indexOf(employeeSelection);
            groupTableList.get(groupIndex).getEmployeeList().get(employeeIndex).setSurname(newSurname);

            //database update
            updateData(groupIndex);
        }

        employeeTable.refresh();

        Platform.runLater(() ->
        {
            event.getTableView().requestFocus();
        });
    }

    private void updateEmployeeCondition(TableColumn.CellEditEvent<Employee, EmployeeCondition> event)
    {
        //get indecies
        int groupIndex = groupTableList.indexOf(groupSelection);
        int employeeIndex = groupTableList.get(groupIndex).getEmployeeList().indexOf(employeeSelection);

        //update table
        groupTableList.get(groupIndex).getEmployeeList().get(employeeIndex).setCondition(event.getNewValue());
        employeeTable.refresh();

        //update database
        updateData(groupIndex);

        Platform.runLater(() ->
        {
            event.getTableView().requestFocus();
        });
    }

    private void updateEmployeeBirth(TableColumn.CellEditEvent<Employee, Integer> event)
    {

        //get indecies
        int groupIndex = groupTableList.indexOf(groupSelection);
        int employeeIndex = groupTableList.get(groupIndex).getEmployeeList().indexOf(employeeSelection);

        //handle null as 0
        Integer new_val_obj = event.getNewValue();
        int newValue = (new_val_obj != null) ? new_val_obj : 0;


        if(newValue < 0)
            System.out.println("ILLEGAL: age parameter lower than 0");
        else
        {
            //update table
            groupTableList.get(groupIndex).getEmployeeList().get(employeeIndex).setBirth_year(newValue);

            //update database
            updateData(groupIndex);
        }

        employeeTable.refresh();

        Platform.runLater(() ->
        {
            event.getTableView().requestFocus();
        });
    }

    private void updateEmployeeSalary(TableColumn.CellEditEvent<Employee, Double> event)
    {
        //get indecies
        int groupIndex = groupTableList.indexOf(groupSelection);
        int employeeIndex = groupTableList.get(groupIndex).getEmployeeList().indexOf(employeeSelection);

        //handle null as 0
        Double new_val_obj = event.getNewValue();
        double newValue = (new_val_obj != null) ? new_val_obj : 0;

        if(newValue < 0)
            System.out.println("ILLEGAL: invalid argument for salary field");
        else
        {
            //update table
            groupTableList.get(groupIndex).getEmployeeList().get(employeeIndex).setSalary(newValue);

            //update database
            updateData(groupIndex);
        }

        employeeTable.refresh();

        Platform.runLater(() ->
        {
            event.getTableView().requestFocus();
        });
    }

    @FXML private void filterList()
    {
        if(groupSelection == null)
            return;

        String argument = filterField.getText();
        List<Employee> filteredList = groupSelection.searchPartial(argument);
        updateEmployeeTable(filteredList);

        employeeLabel.setText(NO_EMPLOYEES_FOUND_TEXT);
    }

    //groupTable events
    @FXML public void editGroupEvent()
    {
        if(groupTable.isEditable())
        {
            groupTable.setEditable(false);
            groupModify.setText(UNLOCK_MODIFY);
        }
        else
        {
            groupTable.setEditable(true);
            groupModify.setText(LOCK_MODIFY);
        }

        groupTable.refresh();
        employeeTable.refresh();
    }

    @FXML private void addGroupEvent()
    {
        //utworzenie nowej grupy
        ClassEmployee group = new ClassEmployee("NEW WORKGROUP", 0);

        //dodanie grupy do tabeli
        groupTableList.add(group);
        groupTable.refresh();

        //dodanie grupy do bazy danych
        addData(group);

        //automatyczne uruchomienie edycji
        if(!groupTable.isEditable())
            editGroupEvent();
    }

    @FXML private void deleteGroupEvent()
    {
        if(groupSelection == null)
            return;

        if(groupSelection.getEmployeeList().isEmpty())
        {
            //delete from database
            removeData(groupSelection);

            //delete from table
            int index = groupTableList.indexOf(groupSelection);
            groupTableList.remove(index);
            groupTable.refresh();

            //set global var to null
            groupSelection = null;

            //change selection to previous neighbour if exists in order to align with UI
            if(!groupTableList.isEmpty())
            {
                if(index == 0)
                    onGroupChange(groupTableList.get(index));
                else
                    onGroupChange(groupTableList.get(index - 1));
            }

            //disable editing when deleting
            if(groupTable.isEditable())
                editGroupEvent();
        }
        else
            System.out.println("ILLEGAL: attempted deletion of a not empty group");
    }

    @FXML private void submitReviewEvent()
    {
        // Create a new rating
        int score = scoreBox.getValue();
        String comment = commentArea.getText();

        Rate rate = new Rate(score, groupSelection, comment);

        // Add new rating to group and update database
        groupSelection.getRatingList().add(rate);
        updateData(groups_arr.indexOf(groupSelection));
        groupTable.refresh();

        // Go back to defaults
        commentArea.setText("");
        scoreBox.setValue(default_rating);
    }

    //Employee table events
    @FXML public void addEmployeeEvent()
    {
        if(groupSelection == null)
            return;

        if(groupSelection.getEmployeeList().size() == groupSelection.getMaxEmployees())
        {
            System.out.println("ILLEGAL: attempted employee addition to an already full group");
            return;
        }

        //create a new employee
        Employee employee = new Employee("name", "surname", EmployeeCondition.NIEOBECNY, 0, 0);

        //get index
        int index = groupTableList.indexOf(groupSelection);

        //update the table
        groupSelection.addEmployee(employee);
        groupTableList.set(index, groupSelection);
        groupTable.refresh();

        //update databse
        updateData(index);

        //automatically unlock employee table
        if(!employeeTable.isEditable())
            editEmployeeEvent();

        Platform.runLater(() ->
        {
            employeeTable.requestFocus();
        });
    }

    @FXML private void editEmployeeEvent()
    {
        if(employeeTable.isEditable())
        {
            employeeTable.setEditable(false);
            employeeModify.setText(UNLOCK_MODIFY);
        }
        else
        {
            employeeTable.setEditable(true);
            employeeModify.setText(LOCK_MODIFY);
        }

        employeeTable.refresh();
    }

    @FXML private void deleteEmployeeEvent()
    {
        if(employeeSelection == null)
            return;

        //delete the employee from table
        int groupIndex = groupTableList.indexOf(groupSelection);
        int employeeIndex = groupTableList.get(groupIndex).getEmployeeList().indexOf(employeeSelection);
        groupTableList.get(groupIndex).getEmployeeList().remove(employeeIndex);
        groupTable.refresh();
        onGroupChange(groupSelection);

        //update the database
        updateData(groupIndex);

        if(!groupTableList.get(groupIndex).getEmployeeList().isEmpty())
        {
            int newIndex;
            if(employeeIndex == 0)
                newIndex = employeeIndex;
            else
                newIndex = employeeIndex - 1;

            //onEmployeeChange(groupTableList.get(groupIndex).getEmployeeList().get(employeeIndex - newIndex));
            employeeTable.getSelectionModel().select(newIndex);
        }
        else
            employeeSelection = null;

        //disable editing when deleting
        if(employeeTable.isEditable())
            editEmployeeEvent();
    }

    //table to database communication
    void updateData(int index)
    {
        ClassEmployee classEmployee = groupTableList.get(index);
        DatabaseController.updateDB(classEmployee);
        groups_arr.set(index, classEmployee);
    }

    void removeData(ClassEmployee data)
    {
        DatabaseController.removeDB(data);
        groups_arr.remove(data);
    }

    void addData(ClassEmployee data)
    {
        DatabaseController.addData(data);
        groups_arr.add(data);
    }
}