package compindia.videoplayer;

/**
 * Created by compindi on 03-11-2017.
 */

public class ModelVideoPlayer
{
    int videoImage;
    int videoHeading;
    int videoDescription;
    int videoPath;
    public ModelVideoPlayer(int videoImage, int videoHeading, int videoDescription,int videoPath)
    {
        this.videoImage = videoImage;
        this.videoHeading = videoHeading;
        this.videoDescription = videoDescription;
        this.videoPath=videoPath;
    }
    public int getVideoImage()
    {
        return videoImage;
    }
    public int getVideoHeading()
    {
        return videoHeading;
    }
    public int getVideoDescription()
    {
        return videoDescription;
    }
    public int getVideoPath()
    {
        return videoPath;
    }
}
