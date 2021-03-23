package com.syntaxphoenix.syntaxapi.utils.general;

public class Status {

    public static final Status EMPTY = new Status(true);

    public static final Status create() {
        return new Status(false);
    };

    private final Object sync = new Object();

    private int total = 0;
    private int failed = 0;
    private int success = 0;
    private int skipped = 0;
    private int cancelled = 0;

    private int marked = 0;

    private boolean done = false;

    /**
     * Constructs a Status with an specific done state
     * 
     * @param loaded - defines if it was loaded or not
     */
    private Status(boolean done) {
        synchronized (sync) {
            this.done = done;
        }
    }

    /**
     * Constructs a Status
     * 
     * @param total - starting total amount of objects to load
     */
    public Status(int total) {
        synchronized (sync) {
            this.total = total;
        }
    }

    /**
     * Set the loading to done
     */
    public void done() {
        synchronized (sync) {
            done = true;
        }
    }

    /**
     * Checks if loading is completly done
     * 
     * @return if loading is done
     */
    public boolean isDone() {
        synchronized (sync) {
            return done;
        }
    }

    /**
     * Marks one object as successful
     * 
     * @return if it was marked or not
     */
    public boolean success() {
        if (isDone() || !mark()) {
            return false;
        }
        synchronized (sync) {
            success++;
        }
        return true;
    }

    /**
     * Marks one object as successful
     * 
     * @return if it was marked or not
     */
    public boolean failed() {
        if (isDone() || !mark()) {
            return false;
        }
        synchronized (sync) {
            failed++;
        }
        return true;
    }

    /**
     * Marks one object as skipped
     * 
     * @return if it was marked or not
     */
    public boolean skip() {
        if (isDone() || !mark()) {
            return false;
        }
        synchronized (sync) {
            skipped++;
        }
        return true;
    }

    /**
     * Marks one object as cancelled
     * 
     * @return if it was marked or not
     */
    public boolean cancel() {
        if (isDone() || !mark()) {
            return false;
        }
        synchronized (sync) {
            cancelled++;
        }
        return true;
    }

    /**
     * Marks an object
     * 
     * @return if it was marked or not
     */
    private boolean mark() {
        if (marked == total) {
            return false;
        }
        synchronized (sync) {
            marked++;
        }
        return true;
    }

    /**
     * Add one object to total objects to load
     */
    public void add() {
        add(1);
    }

    /**
     * Add amount to total objects to load
     *
     * @param amount - amount to add
     */
    public void add(int amount) {
        if (isDone()) {
            return;
        }
        synchronized (sync) {
            total += amount;
        }
    }

    /**
     * Add LoadingStatus to this status
     * 
     * @param status - LoadingStatus to add
     */
    public void add(Status status) {
        if (isDone()) {
            return;
        }
        synchronized (sync) {
            total += status.total;
            failed += status.failed;
            marked += status.marked;
            success += status.success;
            skipped += status.skipped;
            cancelled += status.cancelled;
        }
    }

    /**
     * Get the total amount of objects to load
     * 
     * @return the total amount
     */
    public int getTotal() {
        synchronized (sync) {
            return total;
        }
    }

    /**
     * Get the total amount of objects that were marked
     * 
     * @return the marked amount
     */
    public int getMarked() {
        synchronized (sync) {
            return marked;
        }
    }

    /**
     * Get the total amount of objects that failed to load
     * 
     * @return the failed amount
     */
    public int getFailed() {
        synchronized (sync) {
            return failed;
        }
    }

    /**
     * Get the amount of objects that we successfully loaded
     * 
     * @return the successful amount
     */
    public int getSuccess() {
        synchronized (sync) {
            return success;
        }
    }

    /**
     * Get the amount of objects that were not loaded
     * 
     * @return the not loaded amount
     */
    public int getSkipped() {
        synchronized (sync) {
            return skipped;
        }
    }

    /**
     * Get the amount of objects that were cancelled on load
     * 
     * @return the cancelled amount
     */
    public int getCancelled() {
        synchronized (sync) {
            return cancelled;
        }
    }

}
