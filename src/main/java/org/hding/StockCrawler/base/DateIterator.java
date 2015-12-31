package SCPackage;

import SCPackage.Date;
import java.io.File;

public class DateIterator {
    private Date curr;
    private Date end;
    private Date tnext;
    public DateIterator(Date start) {
        curr = new Date(start);
        end = null;
        tnext = null;
    }
    public DateIterator(Date start, Date end) {
        curr = new Date(start);
        this.end = new Date(end);
        tnext = null;
    }
    private void inc() {
        //max 28
        //28 -> 29 -> +1, 1 
        //27 -> 28 -> +0, 28
        tnext = new Date(curr);
        tnext.day++;
        tnext.month += (tnext.day - 1) / tnext.maxDay(curr);
        tnext.day = (tnext.day - 1) % tnext.maxDay(curr) + 1;
        tnext.year += (tnext.month - 1) / 12;
        tnext.month = (tnext.month - 1) % 12 + 1;
    }
    public boolean hasNext() {
        inc();
        if (end == null) {
            return true;
        }
        if (tnext.compareTo(end) <= 0) {
            return true;
        }
        return false;
    }
    public Date next() {
        curr = tnext;
        return curr;
    }
}