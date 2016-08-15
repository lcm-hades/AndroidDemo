package HPhotoAlbum.Model;

/**
 * Created by Hades on 2016/5/30.
 */
public class FolderBean {

    private String dir;
    private String firstImgPath;
    private String name;
    private int count;

    public String getDir() {
        return dir;
    }

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.indexOf("/");
        this.name = dir.substring(lastIndexOf);
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
