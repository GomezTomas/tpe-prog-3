package tpe;

import tpe.tareas.Tarea;

public class Tree {

    private TreeNode root;
    private int cantTareas;
    private int cantTareasCriticas;

    public Tree() {
        this.root = null;
        this.cantTareasCriticas = 0;
        this.cantTareas = 0;
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public void add(Tarea tarea){
        if(this.root == null){
            this.root = new TreeNode(tarea);
            if(tarea.isCritica()){
                this.cantTareasCriticas++;
            }
            this.cantTareas++;
        } else {
            this.add(this.root, tarea);
        }
    }
    private void add(TreeNode actual, Tarea tarea){
        if (tarea.compareTo(actual.getTarea()) < 0){
            if (actual.getLeft() == null){
                TreeNode temp = new TreeNode(tarea);
                actual.setLeft(temp);
                if(tarea.isCritica()){
                    this.cantTareasCriticas++;
                }
                this.cantTareas++;
            } else {
                add(actual.getLeft(), tarea);
            }
        } else if (tarea.compareTo(actual.getTarea()) >= 0){
            if (actual.getRight() == null){
                TreeNode temp = new TreeNode(tarea);
                actual.setRight(temp);
                if(tarea.isCritica()){
                    this.cantTareasCriticas++;
                }
                this.cantTareas++;
            } else {
                add(actual.getRight(), tarea);
            }
        }
    }

    public boolean isEmpty(){
        return this.root == null;
    }
    public int getCantTareas(){
        return this.cantTareas;
    }
    public int getCantTareasCriticas(){
        return this.cantTareasCriticas;
    }
    public void printPreOrder(){
        String response = null;
        if (this.root == null){
            System.out.println("Arbol vacio");
        } else {
            response = preOrder(this.root);
        }
        System.out.println(response);
    }
    private String preOrder(TreeNode actual){
        if (actual == null){
            return "-";
        } else {
            return actual.getTarea().toString() +" "+ preOrder(actual.getLeft())+" "+ preOrder(actual.getRight());
        }
    }


}
