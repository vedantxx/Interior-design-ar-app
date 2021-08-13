package com.example.interiordesign;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
    int countdesk=0;
    int countarmchair=0;
    int countbed=0;
    int countbookshelf=0;
    int countshortcouch=0;

    AnchorNode anchorNode;

    private enum Models {
        DESK,
        CHAIR,
        BED,
        BOOKSHELF,
        COUCH,
        FLUFFY_CHAIR,
        LONG_COUCH,
    }
    private Models models = Models.DESK;
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
            if (models == Models.DESK) {
                placeDesk(hitResult.createAnchor());
                countdesk++;
            }
            else if (models == Models.CHAIR) {
                placeChair(hitResult.createAnchor());
                countarmchair++;
            }
            else if (models == Models.BED) {
                placeBed(hitResult.createAnchor());
                countbed++;
            }
            else if (models == Models.BOOKSHELF) {
                placeBookshelf(hitResult.createAnchor());
                countbookshelf++;
            }
            else if (models == Models.COUCH) {
                placeCouch(hitResult.createAnchor());
                countshortcouch++;
            }
            else if (models == Models.FLUFFY_CHAIR)
                placeFluffyChair(hitResult.createAnchor());
            else if (models == Models.LONG_COUCH)
                placeLongCouch(hitResult.createAnchor());

        });
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_desk:
                models = Models.DESK;
                Toast.makeText(getApplicationContext(), "desk chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.nav_armchair:
                models = Models.CHAIR;
                Toast.makeText(getApplicationContext(), "Arm chair chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.nav_bed:
                models = Models.BED;
                Toast.makeText(getApplicationContext(), "bed chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.nav_bookshelf:
                models = Models.BOOKSHELF;
                Toast.makeText(getApplicationContext(), "bookshelf chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.nav_shortcouch:
                models = Models.COUCH;
                Toast.makeText(getApplicationContext(), "Short Couch chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.nav_fluffychair:
                models = Models.FLUFFY_CHAIR;
                Toast.makeText(getApplicationContext(), "Fluffy chair chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.nav_longcouch:
                models = Models.LONG_COUCH;
                Toast.makeText(getApplicationContext(), "Long Couch chosen", Toast.LENGTH_LONG).show();
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
    private void placeChair(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("Armchair.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeArcade(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("arcade_machine.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeBar(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("breakfast_bar.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeCloset(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("closet.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeCornerTable(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("corner_table.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeDrums(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("drums.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeBench(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("garden_bench.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placePiano(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("piano.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeSMBed(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("sm_bed.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeSMCloset(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("sm_closet.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeTennis(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("table_tennis_table.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeWalldesk(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("wall_desk.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeShelf(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("wall_shelf.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeWoodenTable(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("wood_table.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeTVTable(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("TV_Table.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeLongCouch(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("Long_Couch.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeFluffyChair(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("Fluffy_Chair.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeCouch(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("Couch.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeBookshelf(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("Bookshelf.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeBed(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("Bed.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeDesk(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("Desk.sfb")).build().thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable)).exceptionally(throwable -> {
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
        countdesk=0;
        countarmchair=0;
        countbed=0;
        countbookshelf=0;
        countshortcouch=0;
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
         countdesk=0;
         countarmchair=0;
         countbed=0;
         countbookshelf=0;
         countshortcouch=0;
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
