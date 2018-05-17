package lab1;

import java.util.List;

/**
 *
 * @author Instlogin
 */
public abstract class AbstractDAO<T> {
    
    public abstract int save(T record);
    
    public abstract List<T> getAll();
    
    public abstract T getById(Object id);
    
    public abstract int deleteById(Object id);
}
