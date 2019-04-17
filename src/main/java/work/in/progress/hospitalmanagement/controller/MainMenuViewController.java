package work.in.progress.hospitalmanagement.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class MainMenuViewController extends AbstractViewController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
