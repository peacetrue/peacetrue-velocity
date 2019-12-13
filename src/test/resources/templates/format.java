$count
        #set($count=$count-1)
        #if($count>0)
        #parse("parse.vm")
        #else
        All done with parsefoo.vm!
        #end