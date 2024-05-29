package tpe;

import tpe.tareas.Tarea;

public class TreeNode{

    private Tarea tarea;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(Tarea tarea) {
        this.tarea = tarea;
        this.left = null;
        this.right = null;
    }

    public Tarea getTarea() {
        return tarea;
    }
    public TreeNode getLeft() {
        return left;
    }
    public TreeNode getRight() {
        return right;
    }
    public void setLeft(TreeNode left) {
        this.left = left;
    }
    public void setRight(TreeNode right) {
        this.right = right;
    }

}
