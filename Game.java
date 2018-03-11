
public abstract class Game
{
    protected boolean doContinue = true;
    public final void run(String[] names)
    {
        initialize();
        loadContent(names);
        draw();
        while (doContinue)
		{
             userAction();
             doContinue = evaluateState();
             draw();
        }
        report();
    }
 
    protected void initialize() { }
    protected void loadContent(String[] name) { }
    protected void userAction() { }
    protected void draw() { }
    protected boolean evaluateState() { return false; }
    protected void report() {};
}
