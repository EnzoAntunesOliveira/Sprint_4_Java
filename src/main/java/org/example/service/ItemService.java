package org.example.service;

import org.example.entities.Endereco;
import org.example.entities.Item;
import org.example.repositories.ItemRepository;

import java.util.List;

public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Endereco create(Item item) {
        if (item == null || item.getNome() == null) {
            throw new IllegalArgumentException("Item e nome são obrigatórios");
        }
        return itemRepository.Create(item);
    }

    public List<Item> readAll() {
        return itemRepository.ReadAll();
    }

    public Item readById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return itemRepository.ReadById(id);
    }

    public boolean updateById(Item item, int id) {
        if (item == null || item.getNome() == null || id <= 0) {
            throw new IllegalArgumentException("Item, nome e ID são obrigatórios");
        }
        return itemRepository.UpdateById(item, id);
    }

    public boolean deleteById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return itemRepository.DeleteById(id);
    }
}