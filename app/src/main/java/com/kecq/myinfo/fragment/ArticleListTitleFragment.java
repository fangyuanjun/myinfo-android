package com.kecq.myinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kecq.myinfo.R;

import org.w3c.dom.Text;


/**
 * Created by fyj on 2017/12/16.
 */

public class ArticleListTitleFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View view =inflater.inflate(R.layout.article_list_title, container,false);

        TextView view=new TextView(getActivity());
        view.setText("ArticleListTitleFragment");
        return  view;
    }

}

