package com.barrygithub.rickandmortyapp.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.barrygithub.rickandmortyapp.R;
import com.barrygithub.rickandmortyapp.common.Constants;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.data.RemoteDatasource.entities.Episode;
import com.barrygithub.rickandmortyapp.databinding.ActivityDetailBinding;
import com.barrygithub.rickandmortyapp.ui.viewModel.ViewModelMain;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding bind;
    private Character character=new Character();
    private ViewModelMain viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Detail");
        super.onCreate(savedInstanceState);
        bind=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        setUpViewModel();
        getBundle();
    }

    private void getBundle(){
        assert getIntent() != null;
        character=getIntent().getExtras().getParcelable(Constants.EXTRA_BUNDLE);
        setUpDetails(character);
    }
    private void setUpDetails(Character c){

        Glide.with(this)
                .load(c.getImage())
                .placeholder(R.drawable.placeholder_error_image)
                .error(R.drawable.placeholder_error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bind.ivDetail);
        bind.tvName.setText(c.getName());
        bind.tvGender.setText(format("Gender", c.getGender()));
        bind.tvSpecie.setText(format("Specie", c.getSpecies()));
        bind.tvStatus.setText(format("Status",c.getStatus()));
        bind.tvType.setText(format("Type",c.getType()));
        bind.toolbarMain.setTitle(c.getName());

        if(c.getEpisodes() !=null){
            for(String s:c.getEpisodes()){
                int idEpisode=Integer.parseInt(s.substring(s.lastIndexOf('/') + 1));
                viewModel.callEpisode(idEpisode);
            }
        }
    }
    private String format(String title, String value){
        return String.format("%s: %s",title,value);
    }
    private void setUpViewModel(){
        viewModel= new ViewModelProvider(this).get(ViewModelMain.class);
        viewModel.getEpisode().observe(this, this::addViewForEpisode);
        viewModel.isLoadingEpisode().observe(this,(isLoading)->{
            if(!isLoading) bind.pbEpisodesLoading.setVisibility(View.GONE);
        });
    }
    private void addViewForEpisode(Episode ep){
        TextView tv = new TextView(this);
        tv.setText(format(ep.getEpisode(),ep.getName()));
        bind.lnEpisodes.addView(tv);
    }
    @Override
    protected void onDestroy() {
        if(!viewModel.disposable.isDisposed()){
            viewModel.disposable.dispose();
        }
        super.onDestroy();
    }
}