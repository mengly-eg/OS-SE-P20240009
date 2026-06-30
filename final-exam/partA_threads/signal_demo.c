#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

void handle_shutdown_signals(int signal_number) {
    if (signal_number == SIGINT) {
        printf("\n[QUANTUMTECH OPERATOR] Interactive interrupt (SIGINT/Ctrl+C) captured successfully.\n");
    } else if (signal_number == SIGTERM) {
        printf("\n[QUANTUMTECH OPERATOR] Polite termination request (SIGTERM) captured successfully.\n");
    }
    printf("Executing runtime object cleanups. Graceful exit completed.\n");
    exit(0);
}

int main() {
    // Attach signal intercept traps
    signal(SIGINT, handle_shutdown_signals);
    signal(SIGTERM, handle_shutdown_signals);

    printf("QuantumTech System Monitor Engine online. Process PID: %d. Awaiting operational flags...\n", getpid());
    while (1) {
        sleep(1);
    }
    return 0;
}
