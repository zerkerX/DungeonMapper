/*
 * Copyright 2010 Ryan Armstrong
 * 
 * This file is part of Dungeon Mapper
 * 
 * Dungeon Mapper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Dungeon Mapper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Dungeon Mapper; If not, see <http://www.gnu.org/licenses/>.
 */
 package dungeonmapper;

import dungeonmapper.map.Map;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class DungeonMapperView extends FrameView {
    
    MapView mapview;
    File activeFile = null;
    String baseName;

    public DungeonMapperView(SingleFrameApplication app)
    {
        super(app);

        initComponents();
        
        fc.setFileFilter(new DungeonFileFilter());
        
        mapview = new MapView(new Map(64, 64, 5));
        setComponent(mapview);
        
        org.jdesktop.application.ResourceMap appResources = app.
                    getContext().getResourceMap(DungeonMapperApp.class);
        baseName = appResources.getString("Application.title");
        updateTitle("(Untitled)", false);
    }
    
    public void updateTitle(String file, boolean dirty)
    {
        /*JFrame mainFrame = DungeonMapperApp.getApplication().getMainFrame();
        
        if (dirty)
            mainFrame.setTitle(baseName + " - " + file + "*");
        else
            mainFrame.setTitle(baseName + " - " + file);*/
    }

    @Action
    public void showAboutBox()
    {
        if (aboutBox == null)
        {
            JFrame mainFrame = DungeonMapperApp.getApplication().getMainFrame();
            aboutBox = new DungeonMapperAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DungeonMapperApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(dungeonmapper.DungeonMapperApp.class).getContext().getResourceMap(DungeonMapperView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(dungeonmapper.DungeonMapperApp.class).getContext().getActionMap(DungeonMapperView.class, this);
        newMenuItem.setAction(actionMap.get("newMap")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        fileMenu.add(newMenuItem);

        openMenuItem.setAction(actionMap.get("openMap")); // NOI18N
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        fileMenu.add(openMenuItem);

        saveMenuItem.setAction(actionMap.get("saveMap")); // NOI18N
        saveMenuItem.setText(resourceMap.getString("saveMenuItem.text")); // NOI18N
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setAction(actionMap.get("saveMapAs")); // NOI18N
        saveAsMenuItem.setText(resourceMap.getString("saveAsMenuItem.text")); // NOI18N
        saveAsMenuItem.setName("saveAsMenuItem"); // NOI18N
        fileMenu.add(saveAsMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void newMap()
    {
        JFrame mainFrame = DungeonMapperApp.getApplication().getMainFrame();
        NewMapSizes newMap = new NewMapSizes(mainFrame, true);
        newMap.setLocationRelativeTo(mainFrame);
        DungeonMapperApp.getApplication().show(newMap);
        
        if (newMap.getAccepted())
        {
            mapview.setMap(new Map(newMap.getMapWidth(), newMap.getMapHeight(), newMap.getMapFloors()));
            activeFile = null;
            updateTitle("(Untitled)", false);
        }
        
        newMap.dispose();
    }

    @Action
    public void openMap()
    {   
        JFrame mainFrame = DungeonMapperApp.getApplication().getMainFrame();
        
        if (fc.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                Map newMap = Map.loadMap(fc.getSelectedFile());
                mapview.setMap(newMap);
                activeFile = fc.getSelectedFile();
                updateTitle(activeFile.getName(), false);
            }
            catch (Exception ex)
            {
                Logger.getLogger(DungeonMapperView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Action
    public void saveMap()
    {
        if (activeFile == null)
            saveMapAs();
        else
        {
            try
            {
                mapview.getMap().saveMap(activeFile);
            }
            catch (IOException ex)
            {
                Logger.getLogger(DungeonMapperView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Action
    public void saveMapAs()
    {
        JFrame mainFrame = DungeonMapperApp.getApplication().getMainFrame();
        
        if (fc.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                mapview.getMap().saveMap(fc.getSelectedFile());
                activeFile = fc.getSelectedFile();
                updateTitle(activeFile.getName(), false);
            }
            catch (IOException ex)
            {
                Logger.getLogger(DungeonMapperView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables

    private JDialog aboutBox;
    private final JFileChooser fc = new JFileChooser();

}
