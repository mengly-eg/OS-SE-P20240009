#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

// Worker thread function
void* worker_func(void* arg) {
    long id = (long)arg;
    long computed_value = id * 100; // Example computed value
    
    // Each thread prints its loop ID, its unique system thread ID, and its result
    printf("Worker %ld: Thread ID = %lu, Computed Value = %ld\n", 
           id, (unsigned long)pthread_self(), computed_value);
    
    // Return the computed value back to the main thread
    pthread_exit((void*)computed_value);
}

int main() {
    pthread_t threads[4];
    long summary = 0;

    // 1. Spawn exactly 4 worker threads
    for (long i = 0; i < 4; i++) {
        if (pthread_create(&threads[i], NULL, worker_func, (void*)i) != 0) {
            perror("Failed to create thread");
            return 1;
        }
    }

    // 2. Main thread joins all 4 threads and collects results
    for (int i = 0; i < 4; i++) {
        void* result;
        pthread_join(threads[i], &result);
        summary += (long)result; // Summarize collected results
    }

    // 3. Print the final summary
    printf("Final Summary Score: %ld\n", summary);
    return 0;
}
