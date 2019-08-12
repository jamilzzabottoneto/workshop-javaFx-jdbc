package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import GUI.util.Alerts;
import GUI.util.Constraints;
import GUI.util.Utils;
import db.DbException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Department;
import model.service.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity;
	
	private DepartmentService service;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtname;

	@FXML
	private Label lblErrorName;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnCancel;

	public void setDepartment(Department entity) {
		this.entity = entity;
		service.saveOrUpdate(entity);
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		}catch(DbException e) {
			Alerts.showAlert("ERRO SAVING OBJECT", null, e.getMessage(), AlertType.ERROR);
		}
		
		
	}

	private Department getFormData() {
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtname.getText());
		return obj;
	}

	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {

		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtname, 30);
	}

	public void updateFormData() {

		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtId.setText(String.valueOf(entity.getId()));
		txtname.setText(entity.getName());
	}

}
