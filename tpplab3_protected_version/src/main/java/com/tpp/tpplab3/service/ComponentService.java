package com.tpp.tpplab3.service;

import com.tpp.tpplab3.models.Component;
import com.tpp.tpplab3.repository.ComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComponentService {

    @Autowired
    private ComponentsRepository componentsRepository;

    public List<Component> getAllComponents() {
        return componentsRepository.findAll();
    }

    public Optional<Component> findComponentById(Integer id) {
        return componentsRepository.findById(id);
    }

    public void saveComponent(Component component) {
        componentsRepository.save(component);
    }

    public void updateComponent(Component updatedComponent) {
        Optional<Component> existingComponentOpt = componentsRepository.findById(updatedComponent.getComponentId());

        if (existingComponentOpt.isPresent()) {
            Component existingComponent = existingComponentOpt.get();

            // Оновлюємо всі поля
            existingComponent.setName(updatedComponent.getName());
            existingComponent.setType(updatedComponent.getType());
            existingComponent.setCost(updatedComponent.getCost());
            existingComponent.setDescription(updatedComponent.getDescription());

            componentsRepository.save(existingComponent);
        }
    }

    public void deleteComponentById(Integer id) {
        componentsRepository.deleteById(id);
    }
}

