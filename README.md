This is a Java-based grid dungeon mapping tool. The goal of this program is to be fast, precise and keyboard-driven. The idea is to simply move a cursor around the map and quickly draw walls & floors without constantly switching and clicking all over the place.

Here is the current control scheme:

-   **Arrows** : Move cursor
-   **Page up/Page Down** : Next/Previous Dungeon level
-   **Space** : Draw floor
-   **W, S, A, D** : Draw walls (in specified direction)
-   **X** : Draw glyph (stairs up/down icons only so far)
-   **Q, E** : Select wall type
-   **Z, C** : Select glyph type
-   **F, V** : Select floor type
-   **Plus, Minus** : Zoom in and out

Also, due to the use of S as a functional key, the save/load keys have been remapped to the F-keys. The dungeon size is variable (choose the size when you select "New"), and files can be saved or loaded as .dungeon files. Since it is keyboard based, the dungeon will scroll when you move the cursor to the edge of the screen since I had problems with the OS-based scrolling. Note when drawing, you can hold down the "draw" buttons and move the cursor with the arrows to quickly block in an area/create a corridor, etc.

Development, at present, is done with a Netbeans project.
