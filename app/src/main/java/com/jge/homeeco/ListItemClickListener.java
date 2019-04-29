package com.jge.homeeco;

import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.Person;

public interface ListItemClickListener {
    void onListItemClick(Chore choreIndexClicked);
    void onListItemClick(Person personIndexClicked);
}
