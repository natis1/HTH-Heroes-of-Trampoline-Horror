package Base;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;


public class Main
{
    //Yeah no more globals except this MAP what is this amateur hour?

    //Eli, map is a function.
    //Ex: Instead of:
    /*
           for(Element e : elements)
           {
            e.use();
           }

           you can do

           map(Element::use(), elements);

           except that Element::use() has to have the type Object -> Object (it can't be void)

           It doesn't really work yet but it can be pretty useful
           Also, theres not much wrong with it since its essentially just a library function, kinda like the for loop
     */

    public static void map(Function f, ArrayList<Object> a)
    {
        for (Object o : a)
        {
            f.apply(o);
        }
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(
                () -> {

                    //BOW TO THE VOID OR BE CONSUMED BY IT

                    //Honestly I am so glad nobody has noticed this or the window loader name.

                    //PS since its an elf a Malzahar quote is kinda unfitting, maybe something like:
                    //"A solid giggle should do the trick!"

                    //Literally don't even need to name a windowloader here I don't think. But whatevs

                    //hmmm.................................................................................................................................................................................................................................................................................................................................................................

                    //...................................................................
                    //......................................................
                    //.........

                    //Clearly I am going insane.
                    //OR AM I?

                    //Two steps forward one step to the side.
                    WindowLoader ElvenMasterRulesOverPunyHumans = new WindowLoader();

                }
        );
    }
}