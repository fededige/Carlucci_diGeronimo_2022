#define MAX_HEIGHT 4

typedef struct _SkipList SkipList;
typedef struct _Node Node;

struct _SkipList{
    Node *head;
    unsigned int max_level;
    int (*compare) (void*, void*);
}; 

struct _Node{
    Node **next;
    unsigned int size;
    void *item;
};

void *searchSkipList(SkipList *list, void* I);
void insertSkipList(SkipList *list, void* I);
Node *CreateNode(void* I, unsigned int randomlevel);
unsigned int randomLevel();
SkipList *CreateSkipList(int (*compare) (void *a, void *b));
void free_memory(SkipList *list);
void *list_get(Node *list);