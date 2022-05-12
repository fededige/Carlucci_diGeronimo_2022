#include "skip_list_lib.h"
#include <stdlib.h>
#include <stdio.h>

SkipList *CreateSkipList(int (*compare) (void *a, void *b)){
    if(compare == NULL){
        fprintf(stderr, "CreateSkipList: compare parameter cannot be NULL");
        return NULL;
    }

    SkipList *list = (SkipList*) malloc(sizeof(SkipList));
    if(list == NULL){
        fprintf(stderr, "CreateSkipList: unable to allocate memory for the SkipList");
    }

    /*list->head = (Node*) malloc(sizeof(Node));*/
    list->head = CreateNode(NULL, MAX_HEIGHT);
    /*list->head = NULL;*/
    list->max_level = MAX_HEIGHT;
    list->compare = compare;
    return list;
}

void *searchSkipList(SkipList *list, void* I){
    Node *x = list->head;
    unsigned int i=0;
    
    for(i = list->max_level; i > 0; i--){
        while(x->next[i]->item < I){
            x = x->next[i];
        }
    }
    x = x->next[1];
    if(x->item == I){
        return x->item;
    }
    else{
        return NULL;
    }
}

void insertSkipList(SkipList *list, void* I){
    Node *new;
    new = CreateNode(I, randomLevel());
    if(new->size > list->max_level){
        list->max_level = new->size;
    }
    if(list->head == NULL){
      list->head = new;
      return;
    }
    Node *x = list->head;
    for(unsigned int k = list->max_level; k > 0; k--){
        if(x->next[k - 1] == NULL || I < x->next[k - 1]->item){
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

/*void free_Node(Node *node, unsigned int i){

    if (node == NULL) {
        return;
    }
    if(node->next[i] == NULL){
      free(node->next[i]);
    }
    else{
      free_Node(node->next[i], i);
    }
    free(node->item);
    free(node);
}*/



void free_SkipList(SkipList *list){
    if(list == NULL){
        fprintf(stderr, "free_SkipList: SkipList parameter cannot be NULL");
        return;
    }
    Node *current = list->head;
    while (current != NULL) {
        Node *next = current->next[0];
        free(current->item);
        free(current);
        current=next;
    }
    free(list);
}

Node *CreateNode(void* I, unsigned int randomlevel){
    Node *x;
    x = (Node*) malloc(sizeof(Node));
    x->size = randomlevel;
    x->item = I;
    x->next = (Node**) malloc(sizeof(Node *) * randomlevel);
    for(unsigned int i = 0; i < randomlevel; i++){
      x->next[i] = NULL;
    }
    return x;
}

unsigned int randomLevel(){
    unsigned int lvl = 1;
    while(random() < 0.5 && lvl < MAX_HEIGHT){
        lvl = lvl + 1;
    }
    return lvl;
}