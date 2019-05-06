package com.jge.homeeco;

import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.Models.Prize;

public interface ListItemClickListener {
    void onListItemClick(Chore choreIndexClicked);
    void onListItemClick(Person personIndexClicked);
    void onListItemClick(Prize prizeItemClicked);
}
