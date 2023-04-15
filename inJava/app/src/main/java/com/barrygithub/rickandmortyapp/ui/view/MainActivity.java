package com.barrygithub.rickandmortyapp.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.barrygithub.rickandmortyapp.common.Constants;
import com.barrygithub.rickandmortyapp.common.MyListener;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.MetaData;
import com.barrygithub.rickandmortyapp.databinding.ActivityMainBinding;
import com.barrygithub.rickandmortyapp.ui.adapters.ImagesAdapter;
import com.barrygithub.rickandmortyapp.ui.viewModel.ViewModelMain;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements MyListener {
    private ActivityMainBinding bind;
    private ViewModelMain viewModel;
    private ImagesAdapter adapter;
    private  int CURRENT_PAGE=1;
    private StaggeredGridLayoutManager gridLayoutManager;
    private Boolean isLoading=false;
    private Boolean isLastPage=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        setUpViewModel();
        setUpAdapter();
        setUpObserver();
    }

    private void setUpViewModel(){
        viewModel = new ViewModelProvider(this).get(ViewModelMain.class);
        viewModel.getData(CURRENT_PAGE);

    }
    private void setUpAdapter(){
        gridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        adapter = new ImagesAdapter(this);
        bind.rvMain.setHasFixedSize(true);
        bind.rvMain.setLayoutManager(gridLayoutManager);
        bind.rvMain.setAdapter(adapter);
        bind.rvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                adapter.addLoadingFooter();
               int visibleItemCount= gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int[] firstVisibleItemCount= gridLayoutManager.findFirstVisibleItemPositions(null);
                if(!isLoading && !isLastPage){
                    if((visibleItemCount + firstVisibleItemCount[0])>=totalItemCount && firstVisibleItemCount[0]>=0){
                        CURRENT_PAGE +=1;
                        bind.rvMain.post(()-> viewModel.getData(CURRENT_PAGE));
                        isLoading=true;
                    }
                }

            }
        });
    }
    private void setUpObserver(){
        viewModel.isLoading().observe(this,(isLoading)->{
            if(!isLoading) bind.loadingMain.setVisibility(View.INVISIBLE);
        });
        viewModel.getResponseApi().observe(this,(response)->{
            adapter.removeLoadingFooter();
            if(response !=null) {
                if (response.getInfo().getPages() == CURRENT_PAGE) isLastPage = true;
                adapter.addAll(response.getResults());
            }
            isLoading=false;

        });
        viewModel.getError().observe(this,(message)->{
            Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onClick(Character character) {
         Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra(Constants.EXTRA_BUNDLE,character);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if(!viewModel.disposable.isDisposed()){
            viewModel.disposable.dispose();
        }
        super.onDestroy();

    }
}