package com.telstra.codechallenge.oldestuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UsersData implements Serializable {
    private List<User> items;
    private int requestedCount;
    private int receivedCount;

    public UsersData() {

    }

    public void addIteams(List<User> userData) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.addAll(userData);
    }
}
