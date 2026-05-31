package co.edu.uptc.presenter; 

import co.edu.uptc.interfaces.*;
import co.edu.uptc.model.ManagerApp;
import co.edu.uptc.view.MenuGui;

public class Runner {
    static PresenterInterface presenter; 
    static ModelInterface model; 
    static ViewInterface view; 

    private static void makeMVP(){
        view = MenuGui.getInstance(); 
        presenter = new MainPresenter(); 
        model = ManagerApp.getInstance();  

        presenter.setView(view);
        presenter.setModel(model);
        view.setPresenter(presenter);
    }
    
    public void start() {
        makeMVP(); 
    }
}
