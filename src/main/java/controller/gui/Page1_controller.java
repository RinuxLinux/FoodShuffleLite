package main.java.controller.gui;

import main.java.controller.db.SQLiteDBManager;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Page1_controller {

    public List<String> getTabGenerate() {
        return tabGenerate;
    }

    public void setTabGenerate(List<String> tabGenerate) {
        this.tabGenerate = tabGenerate;
    }

    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Button go;
    @FXML
    private ImageView register;
    @FXML
    private TextField nbPers;
    @FXML
    private TextField nbRepas;
    @FXML
    private CheckBox less30min;
    @FXML
    private CheckBox entre3060min;
    @FXML
    private CheckBox plus1h;
    @FXML
    private TextField exclusion;
    @FXML
    private Button ajouter;
    @FXML
    private CheckBox vegetarien;
    @FXML
    private CheckBox gluten;
    @FXML
    private CheckBox lait;
    @FXML
    private CheckBox arachide;
    @FXML
    private CheckBox soja;
    @FXML
    private CheckBox coque;
    @FXML
    private CheckBox moutarde;
    @FXML
    private CheckBox oeuf;
    @FXML
    private CheckBox mer;
    @FXML
    private CheckBox sesame;
    @FXML
    private CheckBox poisson;

    @FXML
    private ImageView generer;
    @FXML
    private VBox vBoxExclusion;

    private String resultEx = "";

    private List<String> tabGenerate;

    private boolean flagGenerate = false;

    SQLiteDBManager manager = new SQLiteDBManager();
    Page2_controller controller;

    public String addExclus() {
        this.vBoxExclusion.getChildren().add(new Text(exclusion.getText())); //genere un label sur un bouton quand entre un ingr a exclure
        resultEx += "ingredients LIKE \"%" + exclusion.getText() + "%\" and ";
        return resultEx;
        //System.out.println(resultEx);
    }

    @FXML
    public void generate() {
        flagGenerate = true;

        manager.creationRequest("DROP TABLE IF EXISTS temp_A");
        manager.creationRequest("CREATE TABLE temp_A (id int auto_increment primary key not null,"
                + " R_id int(11), Recettes varchar(250), Regimes varchar(250), I_id int(11), Ingredients varchar(250),"
                + " Qtt decimal(10,2), Unites varchar(25), Groupes varchar(100), Pers int(11), Prepa int(11), Cuisson int(11), kcal int(11))");
        manager.creationRequest("INSERT INTO temp_A (R_id, Recettes, Regimes, I_id, Ingredients, Qtt, Unites, Groupes, Pers, Prepa, Cuisson, kcal)"
                + " SELECT R_id, Recettes, Regimes, I_id, Ingredients, Qtt, Unites, Groupes, Pers, Prepa, Cuisson, kcal FROM v_globale");

        String V = "";
        String G = "";
        String L = "";
        String S = "";
        String Se = "";
        String M = "";
        String O = "";
        String A = "";
        String F = "";
        String P = "";
        String C = "";

        String result = "";
        boolean flag = false;

        if (this.vegetarien.isSelected()) {
            //manager.selectReq("SELECT* FROM v_A WHERE regimes LIKE \"%V%\"" );
            V = "regimes LIKE \"%V%\" and ";
            flag = true;
        }
        if (this.gluten.isSelected()) {
            G = "regimes NOT LIKE \"%G%\" and ";
            flag = true;
        }
        if (this.poisson.isSelected()) {
            P = "regimes NOT LIKE \"%P%\" and ";
            flag = true;
        }
        if (this.coque.isSelected()) {
            F = "regimes NOT LIKE \"%F%\" and ";
            flag = true;
        }
        if (this.mer.isSelected()) {
            C = "regimes NOT LIKE \"%C%\" and ";
            flag = true;
        }
        if (this.moutarde.isSelected()) {
            M = "regimes NOT LIKE \"%M%\" and ";
            flag = true;
        }
        if (this.arachide.isSelected()) {
            A = "regimes NOT LIKE \"%A%\" and ";
            flag = true;
        }
        if (this.lait.isSelected()) {
            L = "regimes NOT LIKE \"%L%\" and ";
            flag = true;
        }
        if (this.soja.isSelected()) {
            S = "regimes NOT LIKE \"%S%\" and ";
            flag = true;
        }
        if (this.oeuf.isSelected()) {
            O = "regimes NOT LIKE \"%O%\" and ";
            flag = true;
        }
        if (this.sesame.isSelected()) {
            Se = "regimes NOT LIKE \"%Se%\" and ";
            flag = true;
        }
        result = "WHERE " + V + S + O + Se + P + C + L + G + A + M + F + "\"";

        if (flag = true) {
            result = result.substring(0, (result.length() - 6));
            manager.creationRequest("DROP TABLE IF EXISTS temp_B");
            manager.creationRequest("CREATE TABLE temp_B (id int auto_increment primary key not null,"
                    + " R_id int(11), Recettes varchar(250), Regimes varchar(250), I_id int(11), Ingredients varchar(250),"
                    + " Qtt decimal(10,2), Unites varchar(25), Groupes varchar(100), Pers int(11), Prepa int(11), Cuisson int(11), kcal int(11))");
            manager.creationRequest("INSERT INTO temp_B (R_id, Recettes, Regimes, I_id, Ingredients, Qtt, Unites, Groupes, Pers, Prepa, Cuisson, kcal)"
                    + " SELECT R_id, Recettes, Regimes, I_id, Ingredients, Qtt, Unites, Groupes, Pers, Prepa, Cuisson, kcal FROM temp_A " + result);

            if (resultEx.length() > 0) {
                resultEx = resultEx.substring(0, (resultEx.length() - 5));
                result = "SELECT R_id FROM temp_A WHERE " + resultEx;
                manager.creationRequest("DELETE FROM temp_B WHERE R_id in (" + result + ")");
                manager.selectReq("SELECT id, R_id, recettes FROM temp_B group by R_id");
            } else {
                manager.selectReq("SELECT id, R_id, recettes FROM temp_B group by R_id");
            }
        } else {
            manager.selectReq("SELECT id, R_id, recettes FROM temp_A group by R_id");
        }
        if (flag = false) {
            if (resultEx.length() > 0) {
                resultEx = resultEx.substring(0, (resultEx.length() - 5));
                manager.creationRequest("DROP TABLE IF EXISTS temp_B");
                manager.creationRequest("CREATE TABLE temp_B (id int auto_increment primary key not null,"
                        + " R_id int(11), Recettes varchar(250), Regimes varchar(250), I_id int(11), Ingredients varchar(250),"
                        + " Qtt decimal(10,2), Unites varchar(25), Groupes varchar(100), Pers int(11), Prepa int(11), Cuisson int(11), kcal int(11))");
                manager.creationRequest("INSERT INTO temp_B (R_id, Recettes, Regimes, I_id, Ingredients, Qtt, Unites, Groupes, Pers, Prepa, Cuisson, kcal)"
                        + " SELECT R_id, Recettes, Regimes, I_id, Ingredients, Qtt, Unites, Groupes, Pers, Prepa, Cuisson, kcal FROM temp_A ");
                result = "SELECT R_id FROM temp_A WHERE " + resultEx;
                manager.creationRequest("DELETE FROM temp_B WHERE R_id in (" + result + ")");
                manager.selectReq("SELECT id, R_id, recettes FROM temp_B group by R_id");

            }
        }

//		boolean flag = false;
//		boolean flag2 = false;
//		String result;
//		String result2;
//		List<String> list_regime = new ArrayList<>();
//		List<String> list_tps = new ArrayList<>();
//
//		if (this.vegetarien.isSelected()) {
//			flag = true;
//			list_regime.add("regimes LIKE '%v%'");
//		}
//		if (this.gluten.isSelected()) {
//			flag = true;
//			list_regime.add("regimes NOT LIKE '%g%'");
//		}
//		if (this.poisson.isSelected()) {
//			flag = true;
//			list_regime.add("regimes NOT LIKE '%p%'");
//		}
//		if (this.coque.isSelected()) {
//			flag = true;
//			list_regime.add("regimes NOT LIKE '%f%'");
//		}
//		if (this.mer.isSelected()) {
//			flag = true;
//			list_regime.add("regimes NOT LIKE '%c%'");
//		}
//		if (this.moutarde.isSelected()) {
//			flag = true;
//			list_regime.add("regimes NOT LIKE '%m%'");
//		}
//		if (this.arachide.isSelected()) {
//			flag = true;
//			list_regime.add("regimes NOT LIKE '%a%'");
//		}
//		if (this.lait.isSelected()) {
//			flag = true;
//			list_regime.add("regimes NOT LIKE '%l%'");
//		}
//		if (this.soja.isSelected()) {
//			flag = true;
//			list_regime.add("regimes NOT LIKE '%s%'");
//		}
//		if (this.oeuf.isSelected()) {
//			flag = true;
//			list_regime.add("regimes NOT LIKE '%o%'");
//		}
//		if (this.sesame.isSelected()) {
//			flag = true;
//			list_regime.add("regimes NOT LIKE '%se%'");
//		}
//		if (this.less30min.isSelected()) {
//			flag2 = true;
//			list_tps.add("(Prepa + Cuisson) < 30");
//		}
//
//		if (this.entre3060min.isSelected()) {
//			flag2 = true;
//			list_tps.add("(Prepa + Cuisson) between 30 and 60");
//		}
//
//		if (this.plus1h.isSelected()) {
//			flag2 = true;
//			list_tps.add("(Prepa + Cuisson) > 60");
//		}
//
//		// r affiche tout
//		String r = "select P_id, Recettes from v_globale";
//		if (!flag) {
//			result = "";
//		} else {
//			result = String.join(" AND ", list_regime);
//			r = String.format("%s where %s", r, result);
//		}
//		if (!flag2) {
//			result2 = "";
//		} else {
//			result2 = String.join(" OR ", list_tps);
//			r = String.format("%s and %s", r, result2);
//		}
//		r = String.format("%s group by P_id", r);
//		System.out.println(r);
//		manager.selectReq(r);
        goPage2();
    }

    @FXML
    public void goPage2() {
        if (flagGenerate == true) {
            tabGenerate = manager.selectRequestStrings("SELECT R_id FROM temp_B GROUP BY R_id");

            if (tabGenerate.size() < Integer.parseInt(nbRepas.getText())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);

                alert.setContentText("Au vu des restrictions, le nombre de recettes possibles est inf�rieur au nombre demand�");
                alert.showAndWait();
            }

            DataShare.instance().setSelectedRecetteIds(tabGenerate);
            DataShare.instance().setRepas(nbRepas.getText());
            //System.out.println(DataShare.instance().getRepas());
            //System.out.println(getTabGenerate().size());
            try {
                Stage stage = (Stage) this.register.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../ProjetFspPage2.fxml"));
                Parent root = loader.load();
//				Parent root = FXMLLoader.load(getClass().getResource("../ProjetFspPage2.fxml"));

                Scene scene = this.register.getScene();
                scene = new Scene(root, 800, 600);

                this.controller = loader.getController();

                stage.setScene(scene);
                stage.show();
                this.controller.showImage();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
