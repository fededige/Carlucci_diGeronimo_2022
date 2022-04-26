typedef struct _Array Array; 

Array *array_create(int (*precedes) (void *a, void *b)); //return 1 if a precedes b 0 otherwise
unsigned long array_size(Array *array); //return array size
void array_free_memory(Array *array);
void *array_get(Array *array, unsigned long i);
Array *array_insert(Array *array, void *element);
Array *bi_insertion_sort(Array *array);
Array *quicksort(Array *array, int mode);
int array_is_empty(Array *array);