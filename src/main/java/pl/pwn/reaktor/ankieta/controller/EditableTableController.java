package pl.pwn.reaktor.ankieta.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import pl.pwn.reaktor.ankieta.model.Ankieta;
import pl.pwn.reaktor.ankieta.model.AnkietaFilter;
import pl.pwn.reaktor.ankieta.service.AnkietaService;

import java.io.IOException;
import java.util.List;

public class EditableTableController {

    @FXML
    private TableView<Ankieta> tableAnkieta;

    @FXML
    private TableColumn<Ankieta, Long> colId;

    @FXML
    private TableColumn<Ankieta, String> colName;

    @FXML
    private TableColumn<Ankieta, String> colLastName;

    @FXML
    private TableColumn<Ankieta, String> colMail;

    @FXML
    private TableColumn<Ankieta, String> colPhone;

    @FXML
    private TableColumn<Ankieta, Boolean> colJava;

    @FXML
    private TableColumn<Ankieta, Boolean> colPython;

    @FXML
    private TableColumn<Ankieta, Boolean> colOther;

    @FXML
    private TableColumn<Ankieta, String> colOtherDesc;

    @FXML
    private TableColumn<Ankieta, String> colLanguage;

    @FXML
    private TableColumn<Ankieta, String> colCourse;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField tfFilterMail;

    @FXML
    private ComboBox<String> cmbFilterJava;

    @FXML
    private ComboBox<String> cmbFilterLang;

    @FXML
    private Button btnSelect;

    @FXML
    void back(MouseEvent event) throws IOException {

        Stage adminStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/adminView.fxml"));
        adminStage.setTitle("Panel administratora");
        adminStage.setScene(new Scene(root));
        adminStage.show();

        ((Node) event.getSource()).getScene()
                                  .getWindow()
                                  .hide();

    }

    private AnkietaService ankietaService;

    ObservableList<String> languages = FXCollections.observableArrayList("Select", "Basic", "Intermediate", "Advanced");
    ObservableList<String> java = FXCollections.observableArrayList("Select", "Yes", "No");

    public void initialize() {
        cmbFilterLang.setItems(languages);
        cmbFilterJava.setItems(java);

        fillAnkietaData();

        setCallValue();

        // włączenie edytowania w tabeli
        tableAnkieta.setEditable(true);

        // metoda odpowiedzialne za obsługę edycji naposzczególnych polach
        editCalls();
    }

    private void fillAnkietaData() {
        ankietaService = new AnkietaService();
        List<Ankieta> ankietas = ankietaService.getAllAnkieta();

        ObservableList<Ankieta> data = FXCollections.observableArrayList(ankietas);
        tableAnkieta.setItems(null);
        tableAnkieta.setItems(data);
    }

    private void setCallValue() {

        colId.setCellValueFactory(new PropertyValueFactory<Ankieta, Long>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("name"));
        colLastName.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("lastName"));
        colMail.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("mail"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("phone"));
        colJava.setCellValueFactory(new PropertyValueFactory<Ankieta, Boolean>("java"));
        colPython.setCellValueFactory(new PropertyValueFactory<Ankieta, Boolean>("python"));
        colOther.setCellValueFactory(new PropertyValueFactory<Ankieta, Boolean>("other"));
        colOtherDesc.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("otherDesc"));
        colLanguage.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("language"));
        colCourse.setCellValueFactory(new PropertyValueFactory<Ankieta, String>("course"));
    }

    @FXML
    void delete(MouseEvent event) {

        if (tableAnkieta.getSelectionModel() == null || tableAnkieta.getSelectionModel()
                                                                    .getSelectedItem() == null) {

            Alert error = new Alert(AlertType.ERROR);
            error.setHeaderText("ERROR");
            error.setContentText("Please select Item before clicked Delete");
            error.setTitle("No selected item!");
            error.show();
            return;
        }

        // pobranie zaznaczonego wiersza
        long id = tableAnkieta.getSelectionModel()
                              .getSelectedItem()
                              .getId();
        ankietaService.delete(id);

        fillAnkietaData();
    }

    @FXML
    void select(MouseEvent event) {

        String languageValue = cmbFilterLang.getValue();
        String javaValue = cmbFilterJava.getValue();
        String mailValue = tfFilterMail.getText();

        AnkietaFilter filter = new AnkietaFilter(mailValue, javaValue, languageValue);
        List<Ankieta> list = ankietaService.filter(filter);

        ObservableList<Ankieta> data = FXCollections.observableArrayList(list);
        tableAnkieta.setItems(null);
        tableAnkieta.setItems(data);
    }

    private void editCalls() {
        // edycja kolumny name
        editNameCell();

        // edycja kolumny last name
        editLastNameCell();

        // edycja kolumny mail
        editMailCell();

        // edycja kolumny phone
        editPhoneCell();

        // edycja kolumny java
        editJavaCell();

        // edycja kolumny python
        editPythonCell();

        // edycja kolumny other
        editOtherCell();

        // edycja kolumny otherDesc
        editOtherDescCell();

        // edycja kolumny Language
        editLanguageCell();

        // edycja kolumny course
        editCourseCell();
    }

    private void editNameCell() {
        colName.setCellFactory(TextFieldTableCell.<Ankieta>forTableColumn());
        colName.setOnEditCommit(new EventHandler<CellEditEvent<Ankieta, String>>() {

            public void handle(CellEditEvent<Ankieta, String> t) {
                ((Ankieta) t.getTableView()
                            .getItems()
                            .get(t.getTablePosition()
                                  .getRow())).setName(t.getNewValue());

                Ankieta selectedItem = tableAnkieta.getSelectionModel()
                                                   .getSelectedItem();
                // update wiersza po edycji pola
                updateCell(selectedItem);
            }
        });
    }

    private void editLastNameCell() {
        colLastName.setCellFactory(TextFieldTableCell.<Ankieta>forTableColumn());
        colLastName.setOnEditCommit(new EventHandler<CellEditEvent<Ankieta, String>>() {

            public void handle(CellEditEvent<Ankieta, String> t) {
                ((Ankieta) t.getTableView()
                            .getItems()
                            .get(t.getTablePosition()
                                  .getRow())).setLastName(t.getNewValue());
                Ankieta selectedItem = tableAnkieta.getSelectionModel()
                                                   .getSelectedItem();
                // update wiersza po edycji pola
                updateCell(selectedItem);
            }
        });
    }

    private void editMailCell() {
        colMail.setCellFactory(TextFieldTableCell.<Ankieta>forTableColumn());
        colMail.setOnEditCommit(new EventHandler<CellEditEvent<Ankieta, String>>() {

            public void handle(CellEditEvent<Ankieta, String> t) {
                ((Ankieta) t.getTableView()
                            .getItems()
                            .get(t.getTablePosition()
                                  .getRow())).setMail(t.getNewValue());
                Ankieta selectedItem = tableAnkieta.getSelectionModel()
                                                   .getSelectedItem();
                // update wiersza po edycji pola
                updateCell(selectedItem);
            }
        });
    }

    private void editPhoneCell() {
        colPhone.setCellFactory(TextFieldTableCell.<Ankieta>forTableColumn());
        colPhone.setOnEditCommit(new EventHandler<CellEditEvent<Ankieta, String>>() {

            public void handle(CellEditEvent<Ankieta, String> t) {
                ((Ankieta) t.getTableView()
                            .getItems()
                            .get(t.getTablePosition()
                                  .getRow())).setPhone(t.getNewValue());
                Ankieta selectedItem = tableAnkieta.getSelectionModel()
                                                   .getSelectedItem();
                // update wiersza po edycji pola
                updateCell(selectedItem);
            }
        });
    }

    private void editJavaCell() {
        colJava.setCellValueFactory(new Callback<CellDataFeatures<Ankieta, Boolean>, ObservableValue<Boolean>>() {

            public ObservableValue<Boolean> call(CellDataFeatures<Ankieta, Boolean> param) {
                final Ankieta ankieta = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(ankieta.getJava());
                // When "Java?" column change.
                booleanProp.addListener(new ChangeListener<Boolean>() {

                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        ankieta.setJava(newValue);

                        // update wiersza po edycji pola
                        updateCell(ankieta);
                    }
                });
                return booleanProp;
            }
        });

        colJava.setCellFactory(new Callback<TableColumn<Ankieta, Boolean>, TableCell<Ankieta, Boolean>>() {

            public TableCell<Ankieta, Boolean> call(TableColumn<Ankieta, Boolean> p) {
                CheckBoxTableCell<Ankieta, Boolean> cell = new CheckBoxTableCell<Ankieta, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }

    private void editPythonCell() {
        colPython.setCellValueFactory(new Callback<CellDataFeatures<Ankieta, Boolean>, ObservableValue<Boolean>>() {

            public ObservableValue<Boolean> call(CellDataFeatures<Ankieta, Boolean> param) {
                final Ankieta ankieta = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(ankieta.getPython());
                // When "Python?" column change.
                booleanProp.addListener(new ChangeListener<Boolean>() {

                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        ankieta.setPython(newValue);

                        // update wiersza po edycji pola
                        updateCell(ankieta);
                    }
                });
                return booleanProp;
            }
        });

        colPython.setCellFactory(new Callback<TableColumn<Ankieta, Boolean>, TableCell<Ankieta, Boolean>>() {

            public TableCell<Ankieta, Boolean> call(TableColumn<Ankieta, Boolean> p) {
                CheckBoxTableCell<Ankieta, Boolean> cell = new CheckBoxTableCell<Ankieta, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }

    private void editOtherCell() {
        colOther.setCellValueFactory(new Callback<CellDataFeatures<Ankieta, Boolean>, ObservableValue<Boolean>>() {

            public ObservableValue<Boolean> call(CellDataFeatures<Ankieta, Boolean> param) {
                final Ankieta ankieta = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(ankieta.getOther());
                // When "Other?" column change.
                booleanProp.addListener(new ChangeListener<Boolean>() {

                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        ankieta.setOther(newValue);

                        // update wiersza po edycji pola
                        updateCell(ankieta);
                    }
                });
                return booleanProp;
            }
        });

        colOther.setCellFactory(new Callback<TableColumn<Ankieta, Boolean>, TableCell<Ankieta, Boolean>>() {

            public TableCell<Ankieta, Boolean> call(TableColumn<Ankieta, Boolean> p) {
                CheckBoxTableCell<Ankieta, Boolean> cell = new CheckBoxTableCell<Ankieta, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }

    private void editOtherDescCell() {
        colOtherDesc.setCellFactory(TextFieldTableCell.<Ankieta>forTableColumn());
        colOtherDesc.setOnEditCommit(new EventHandler<CellEditEvent<Ankieta, String>>() {

            public void handle(CellEditEvent<Ankieta, String> t) {
                ((Ankieta) t.getTableView()
                            .getItems()
                            .get(t.getTablePosition()
                                  .getRow()))
                        .setOtherDesc(t.getNewValue());
                Ankieta selectedItem = tableAnkieta.getSelectionModel()
                                                   .getSelectedItem();
                // update wiersza po edycji pola
                updateCell(selectedItem);
            }
        });
    }

    private void editCourseCell() {
        ObservableList<String> courses = FXCollections.observableArrayList("Back-end", "Front-end", "Web developere",
                                                                           "Tester");

        colCourse.setCellValueFactory(new Callback<CellDataFeatures<Ankieta, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(CellDataFeatures<Ankieta, String> param) {
                Ankieta ankieta = param.getValue();

                String course = ankieta.getCourse();
                return new SimpleObjectProperty<String>(course);
            }
        });
        colCourse.setCellFactory(ComboBoxTableCell.<Ankieta, String>forTableColumn(courses));
        colCourse.setOnEditCommit(new EventHandler<CellEditEvent<Ankieta, String>>() {
            public void handle(CellEditEvent<Ankieta, String> event) {
                TablePosition<Ankieta, String> pos = event.getTablePosition();

                String newCourse = event.getNewValue();
                int row = pos.getRow();
                Ankieta ankieta = event.getTableView()
                                       .getItems()
                                       .get(row);
                ankieta.setCourse(newCourse);

                // update wiersza po edycji pola
                EditableTableController.this.updateCell(ankieta);
            }
        });
    }

    private void editLanguageCell() {
        ObservableList<String> langueages = FXCollections.observableArrayList("Basic", "Intermediate", "Advanced");

        colLanguage.setCellValueFactory(new Callback<CellDataFeatures<Ankieta, String>, ObservableValue<String>>() {

            public ObservableValue<String> call(CellDataFeatures<Ankieta, String> param) {
                Ankieta ankieta = param.getValue();

                String course = ankieta.getLanguage();
                return new SimpleObjectProperty<String>(course);
            }
        });
        colLanguage.setCellFactory(ComboBoxTableCell.<Ankieta, String>forTableColumn(langueages));
        colLanguage.setOnEditCommit(new EventHandler<CellEditEvent<Ankieta, String>>() {
            public void handle(CellEditEvent<Ankieta, String> event) {
                TablePosition<Ankieta, String> pos = event.getTablePosition();

                String newLanguage = event.getNewValue();
                int row = pos.getRow();
                Ankieta ankieta = event.getTableView()
                                       .getItems()
                                       .get(row);
                ankieta.setLanguage(newLanguage);
                // update wiersza po edycji pola
                EditableTableController.this.updateCell(ankieta);
            }
        });
    }

    private void updateCell(Ankieta selectedItem) {
        ankietaService.update(selectedItem);
    }

}
