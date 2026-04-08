package nl.miwnn.cohort19.anouk.dinerplannerDemo.service;

import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Menu;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: Anouk de Vos
 * handle all business logic regarding menu's
 */

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void publishMenu(Long menuId) {
        changePublishState(menuId, true);
    }

    public void unpublishMenu(Long menuId) {
        changePublishState(menuId, false);
    }

    private void changePublishState(Long menuId, boolean newState) {

        Optional<Menu> optionalMenu = menuRepository.findById(menuId);

        if (optionalMenu.isEmpty()){
            throw new IllegalArgumentException(String.format("Menu met id %d bestaat niet", menuId));
        }

        Menu menu = optionalMenu.get();

        if (menu.isPublished() == newState) {
            throw new IllegalArgumentException(String.format("Menu is %s gepubliceerd.", newState ? "al" : "nog niet"));
        }

        menu.setPublished(newState);
        menuRepository.save(menu);
    }
}
