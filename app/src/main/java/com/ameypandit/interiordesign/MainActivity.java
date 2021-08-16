package com.ameypandit.interiordesign;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Sun;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    private ArFragment arFragment;


    AnchorNode anchorNode;

    private enum Models {
        LIVINGROOM,
        LIVINGROOM2,
        CHAIR,
        SOFA,
        DESKTOP,
        TABLEANDCHAIR,
        MCHAIR
    }
    private Models models = Models.LIVINGROOM;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arfragment);



        ExtendedFloatingActionButton fabmenu=findViewById(R.id.extmenufab);
        fabmenu.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainActivity.this,view);
                popup.setOnMenuItemClickListener(MainActivity.this);
                popup.inflate(R.menu.main_menu);
                popup.show();
            }
        });
        ExtendedFloatingActionButton fabclear=findViewById(R.id.extclearfab);
        fabclear.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                removeAnchorNode();
                Toast.makeText(getApplicationContext(),"Items Cleared!",Toast.LENGTH_LONG).show();
            }
        });
        ExtendedFloatingActionButton fabclearlast=findViewById(R.id.extlastclrfab);
        fabclearlast.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                removelastAnchorNode();
                Toast.makeText(getApplicationContext(),"Last item cleared!",Toast.LENGTH_LONG).show();
            }
        });

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            if (models == Models.LIVINGROOM) {
                placeLivingroom(hitResult.createAnchor());

            }
            else if (models == Models.LIVINGROOM2) {
                placeLivingroom2(hitResult.createAnchor());

            }
            else if (models == Models.CHAIR) {
                placeChair(hitResult.createAnchor());

            }
            else if (models == Models.SOFA) {
                placeSofa(hitResult.createAnchor());

            }
            else if (models == Models.DESKTOP) {
                placeDesktop(hitResult.createAnchor());

            }
            else if (models == Models.TABLEANDCHAIR) {
                placeTableandchair(hitResult.createAnchor());

            }
            else if (models == Models.MCHAIR) {
                placeMchair(hitResult.createAnchor());

            }

        });
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.livingroom:
                models = Models.LIVINGROOM;
                Toast.makeText(getApplicationContext(), "lr chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.livingroom2:
                models = Models.LIVINGROOM2;
                Toast.makeText(getApplicationContext(), "lr2 chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.chair:
                models = Models.CHAIR;
                Toast.makeText(getApplicationContext(), "chair chosen", Toast.LENGTH_LONG).show();
                return true;
//            case R.id.sofa:
//                models = Models.SOFA;
//                Toast.makeText(getApplicationContext(), "sofa chosen", Toast.LENGTH_LONG).show();
//                return true;
            case R.id.desktop:
                models = Models.DESKTOP;
                Toast.makeText(getApplicationContext(), "desktop chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.tableandchair:
                models = Models.TABLEANDCHAIR;
                Toast.makeText(getApplicationContext(), "table chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.mchair:
                models = Models.MCHAIR;
                Toast.makeText(getApplicationContext(), "mchair chosen", Toast.LENGTH_LONG).show();
                return true;
            default:
                return false;
        }
    }

    private void about(){
        Intent intent3 = new Intent(getApplicationContext(),AboutActivity.class);
        startActivity(intent3);

        Toast.makeText(getApplicationContext(),"About page",Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeLivingroom(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("InteriorTest.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeLivingroom2(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("room.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeChair(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("the chair modeling.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeSofa(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("Sofa_3_obj.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeDesktop(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("desktop.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeTableandchair(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("Table And Chairs.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeMchair(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("modern chair 11 obj.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });

    }




    private void removelastAnchorNode() {
        //Remove an anchor node
        List<Node> children = new ArrayList<>(arFragment.getArSceneView().getScene().getChildren());
        Node x = children.get(children.size() - 1);
        if (x instanceof AnchorNode) {
            if (((AnchorNode) x).getAnchor() != null) {
                Objects.requireNonNull(((AnchorNode) x).getAnchor()).detach();
            }
        }
        if (!(x instanceof Camera) && !(x instanceof Sun)) {
            x.setParent(null);
        }

    }

    private void removeAnchorNode() {
        //Remove an anchor node
        List<Node> children = new ArrayList<>(arFragment.getArSceneView().getScene().getChildren());
        for (Node node : children) {
            if (node instanceof AnchorNode) {
                if (((AnchorNode) node).getAnchor() != null) {
                    Objects.requireNonNull(((AnchorNode) node).getAnchor()).detach();
                }
            }
            if (!(node instanceof Camera) && !(node instanceof Sun)) {
                node.setParent(null);
            }
        }

    }


    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();

    }
}
