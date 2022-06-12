#include "skip_list_lib.h"
#include <stdlib.h>
#include <stdio.h> 
#include <time.h>

//controlla se è vuota
int skipList_is_empty(SkipList *list){
    return list->head->next[0] == NULL;
}
//ritorna la size della skiplist
unsigned int SkipList_size(SkipList *list){
    Node *x = list->head;
    unsigned int k=0;
    while(x != NULL){
        k++;
        x = x->next[0];
    }
    return k - 1; /*-1 perchè non bisogna contare head*/
}
//ritorna l'item contenuto in node
void *list_get(Node *node){ 
    if(node == NULL){
        fprintf(stderr, "list_get: node parameter cannot be NULL");
        return NULL;
    }
    return node->item;
}
//crea una skiplist vuota
SkipList *CreateSkipList(int (*compare) (void *a, void *b)){
    if(compare == NULL){
        fprintf(stderr, "CreateSkipList: compare parameter cannot be NULL");
        return NULL;
    }
    long int seed = time(NULL);
    srand((unsigned int) seed);
    SkipList *list = (SkipList*) malloc(sizeof(SkipList));
    if(list == NULL){
        fprintf(stderr, "CreateSkipList: unable to allocate memory for the SkipList");
    }

    list->head = CreateNode(NULL, MAX_HEIGHT); //crea un nodo di testa con altezza massima, e lo inizializza tutto a NULL
    list->max_level = 0;
    list->compare = compare;
    return list;
}
//cerca nella skiplist l'elemento I
void *searchSkipList(SkipList *list, void* I){
    Node *x = list->head;
    unsigned int i=0;
    
    for(i = list->max_level; i > 0; i--){
        while((x->next[i - 1] != NULL) && list->compare(x->next[i - 1]->item, I) == -1){
            x = x->next[i - 1];
        }
    }

    x = x->next[0];
    
    if((x != NULL) && (list->compare(x->item, I) == 0)){
        return x->item;
    }
    else{
        return NULL;
    }
}
//inserisce nella skiplist l'elemento I
void insertSkipList(SkipList *list, void* I){
    Node *new;
    new = CreateNode(I, randomLevel());
    if(new->size > list->max_level){
        list->max_level = new->size;
    }

    Node *x = list->head;
    for(unsigned int k = list->max_level; k > 0; k--){
        if(x->next[k - 1] == NULL || (list->compare(I, x->next[k - 1]->item) == -1)){
            if(k - 1 < new->size){
                new->next[k - 1] = x->next[k - 1];
                x->next[k - 1] = new;
            }
        }
        else{
            x = x->next[k - 1];
            k++;    
        }
    }
}
//libera la memoria
void free_memory(SkipList *list){ 
    if(list == NULL){
        fprintf(stderr, "free_SkipList: SkipList parameter cannot be NULL");
        return;
    }
    Node *corrente = list->head;
    while (corrente != NULL) {
        Node *successivo = corrente->next[0];
        free(corrente->next);   //libera l'array di next
        free(corrente);         //libera il nodo attuale
        corrente=successivo;
    }
    free(list);
}
//crea una nodo della skiplist
Node *CreateNode(void* I, unsigned int randomlevel){
    Node *x;
    x = (Node*) malloc(sizeof(Node));
    x->size = randomlevel;
    x->item = I;
    x->next = (Node**) malloc(sizeof(Node *) * randomlevel);
    for(unsigned int i = 0; i < randomlevel; i++){ //inizializziamo tutti gli elementi di next a NULL
      x->next[i] = NULL;
    }
    return x;
}
//estrae un numero random tra 0 e RAND_MAX e lo dividiamo per RAND_MAX in modo da ottenere un valore compreso tra 0 e 1
unsigned int randomLevel(){
    unsigned int lvl = 1;    
    while((double)rand() / (double)RAND_MAX < 0.5 && lvl < MAX_HEIGHT){ 
        lvl = lvl + 1;
    }
    return lvl;
}