#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

int main() {
    printf("Real User ID (UID): %d\n", getuid());
    printf("Effective User ID (EUID): %d\n", geteuid());
    return 0;
}
