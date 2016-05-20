package org.jason.gameplayer.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool 
{

    private final static int TERMINATION_TIMEOUT = 2;
    private final static int DEFAULT_POOL_SIZE = 20;
    private ExecutorService pool;
    
    /**
     * Build the threadpool.
     * 
     * @param       poolSize        The size of the threadpool.
     * 
     */
    public ThreadPool(int poolSize) 
    {               
            pool = Executors.newFixedThreadPool(poolSize);
    }
    
    public ThreadPool()
    {
            this(DEFAULT_POOL_SIZE);
    }
    
    /**
     * Execute a task in a thread.
     * 
     * @param task  the task to execute.
     */
    public void runTask(Runnable task)
    {
            pool.execute(task);
            //pool.submit(task);
    }

    /**
     * Shutdown the threadpool safely. Wait for all running threads to terminate.
     */
    public void close()
    {
            //shutdown of a shutdown pool -> noop
    		
    	
            //shutdown prevents new tasks from coming in
            pool.shutdown();
            try 
            {
                    //while(!pool.isTerminated())
                    while(!pool.awaitTermination(TERMINATION_TIMEOUT, TimeUnit.SECONDS))
                    {
                            try
                            {
                                    Thread.sleep(1000);
                            }
                            catch (InterruptedException e) 
                            {
                                    e.printStackTrace();
                            }
                    }
                    
            } 
            catch (InterruptedException e) 
            {
                    e.printStackTrace();
            }
    }

}
