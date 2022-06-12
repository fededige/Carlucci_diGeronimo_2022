#define INITIAL_CAPACITY 2

struct _Array {
  void **array;
  unsigned long size;
  unsigned long array_capacity;
  int (*precedes)(void*, void*);
};

typedef struct _Array Array; 

Array *array_create(int (*precedes) (void *a, void *b));
unsigned long array_size(Array *array);
void array_free_memory(Array *array);
void *array_get(Array *array, unsigned long i);
Array *array_insert(Array *array, void *element);
int array_is_empty(Array *array);