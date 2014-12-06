package pg.android.pendex.beans;

import java.util.Map;

/**
 * Like for personal interests there key is the like string.
 * 
 * @author Sora
 * 
 */
public class Like {

    private String like;
    private Map<String, Integer> likeMap;

    public Like() {

    }

    public String getLike() {
        return like;
    }

    public void setLike(final String like) {
        this.like = like;
    }

    public Map<String, Integer> getLikeMap() {
        return likeMap;
    }

    public void setLikeMap(final Map<String, Integer> likeMap) {
        this.likeMap = likeMap;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((like == null) ? 0 : like.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Like other = (Like) obj;
        if (like == null) {
            if (other.like != null) {
                return false;
            }
        } else if (!like.equals(other.like)) {
            return false;
        }
        return true;
    }

}
