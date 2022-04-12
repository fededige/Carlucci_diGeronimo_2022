typedef struct _Array Array;

Array *binary_insertion_create(int *(precedes) (void *a, void *b)); //return 1 if a precedes b 0 otherwise

