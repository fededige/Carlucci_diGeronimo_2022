#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h> 
#include "skip_list_lib.h"

#define PROGRAM "/bin/skip_list_main"

typedef struct _record{
    char *word;
} Record;
//apertura del file
FILE *open_file(const char *filename, char *m){
    FILE *file = fopen(filename, m);
    if (file == NULL){
        printf("File not found: %s\n", filename);
        exit(1);
    }
    return file;
}
//caricamento della skiplist da file
static void load_SkipList(const char *dictionary, SkipList *list){
    FILE *fp;
    char *line = NULL;
    size_t len = 0;
    fp = open_file(dictionary, "r");
    while (getline(&line, &len, fp) != -1){
        Record *record_p = malloc(sizeof(Record));

        if (record_p == NULL){
            fprintf(stderr, "main: unable to allocate memory for the read record");
            exit(EXIT_FAILURE);
        }

        if (line[strlen(line) - 1] == '\n'){ // facciamo in modo che ogni stringa non finisca con uno \n
            line[strlen(line) - 1] = '\0';
        }

        record_p->word = (char *)malloc((strlen(line) + 1) * sizeof(char));

        if (record_p->word == NULL){
            fprintf(stderr, "main: unable to allocate memory for the string of the read record");
            exit(EXIT_FAILURE);
        }

        strcpy(record_p->word, line);

        insertSkipList(list, (void *)record_p); //chiama il metodo per inserire nella lista il record
    }
    if (line)
        free(line);
    fclose(fp);
    printf("\nData loaded\n\n");
}

void Usage(){
  fprintf(stderr, "\nUSAGE\t: \t%s ", PROGRAM);
  fprintf(stderr, "\"insert dictionary pathname\" ");
  fprintf(stderr, "\"insert text to be corrected pathname\" ");
  exit(1);
}

//liberiamo la memoria
void free_SkipList(SkipList *list){
    if (list == NULL){
        return;
    }
    Node *node = list->head->next[0];
    if (node == NULL){
        free_memory(list);
        return;
    }
    while (node != NULL){
        Record *element = (Record *)list_get(node);
        node = node->next[0];
        free(element->word);
        free(element);
    }
    free_memory(list);
}

void correct_text_with_dictionary(const char *dictionary, const char *file_name,int (*compare)(void *, void *)){
    FILE *fp;
    fp = open_file(file_name, "r");
    char ch = '\n';
    int flag=0;
    char *str;

    SkipList *list = CreateSkipList(compare);
    load_SkipList(dictionary, list);
    
    while(ch != -1){                            //leggiamo carattere per carattere tutte le parole del testo e le cerchiamo una ad una
        ch = (char) fgetc(fp);
        str = (char *) calloc(1, sizeof(char)); //inizializzia la stringa 0 (è richiesto da strncat)
        flag = 0;                               //il flag serve per segnalare se in str c'è una parola oppure punteggiatura, spazi ecc. 
        while((ch >= 'A' && ch <='Z') || (ch >= 'a' && ch <= 'z')){
            if((ch >= 'A' && ch <='Z')){        //tutte le lettere vengono rese minuscole perché nel dizionario non sono presenti maiuscole
                //ch = (char) ch + 32;          //32 è l'offest tra le lettere maiuscole e le lettere minuscole, non funziona a causa di -Wconversion
                ch = (char) tolower(ch);
            }
            flag = 1;
            str = realloc(str, strlen(str) + sizeof(char) +1); //reallochiamo la lunghezza della stringa per permetere di accogliere il prossimo carattere
            str = strncat(str, &ch, 1);          //aggiungiamo il carattere appena letto alla stringa
            ch = (char) fgetc(fp);               //leggiamo il prossimo carattere
        }
        if(flag==1){
            Record *serched_el = malloc(sizeof(Record));
            if (serched_el == NULL){
                fprintf(stderr, "main: unable to allocate memory for the read record");
                exit(EXIT_FAILURE);
            }
            serched_el->word = (char *)malloc((strlen(str) * sizeof(char)) + 1);

            if (serched_el->word == NULL){
                fprintf(stderr, "main: unable to allocate memory for the string of the read record");
                exit(EXIT_FAILURE);
            }

            strcpy(serched_el->word, str);
            
            if(searchSkipList(list, serched_el) == NULL){
                printf("%s\n",serched_el->word); //stampa le parole non presenti a stdout
            }
            free(serched_el->word);
            free(serched_el);
        }
        free(str);
    }
    fclose(fp);
    free_SkipList(list);
}
//relazione di precedenza  
static int compare(void *r1_p, void *r2_p){
    if (r1_p == NULL){
        fprintf(stderr, "precedes_string: the first parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    if (r2_p == NULL){
        fprintf(stderr, "precedes_string: the second parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    Record *rec1_p = (Record *)r1_p;
    Record *rec2_p = (Record *)r2_p;
    int value = strcmp(rec1_p->word, rec2_p->word);
    if (value > 0){
        return 1;
    }
    else if (value < 0){
        return -1;
    }
    return value;
}

int main(int argc, char const *argv[]){
    if (argc < 3){
        Usage();
        exit(EXIT_FAILURE);
    }
    correct_text_with_dictionary(argv[1], argv[2], compare);
}