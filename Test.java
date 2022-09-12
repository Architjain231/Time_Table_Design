import java.util.*;
class Test {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer,String> hm=new HashMap<>();
        hm.put(1,"Monday");
        hm.put(2,"Tuesday");
        hm.put(3,"Wednesday");
        hm.put(4,"Thrusday");
        hm.put(5,"Friday");
        hm.put(6,"Saturday");
        System.out.print("No of subjects : ");
        int noOfSubjects=sc.nextInt();
        HashSet<String> subjects=new HashSet<>(noOfSubjects);
        for(int i=1;i<=noOfSubjects;i++)
        {
            System.out.print("Subject "+i+" : ");
            String sub=sc.next();


            
            System.out.println();
            subjects.add(sub);
        }
        System.out.println("Holidays of this week ?");
        HashSet<Integer> holidays=new HashSet<>();
        System.out.println("Enter");
        for(int i=1;i<=6;i++)
        {
            System.out.println(i+" : "+hm.get(i));
        }
        System.out.println("Enter 0 to exit...");
        int input= sc.nextInt();
        while(input!=0)
        {
            holidays.add(input);
            System.out.println("Holiday Day:"+hm.get(input));
            System.out.println("Enter 0 to exit...");
             input= sc.nextInt();
        }

         TreeMap<Pair,String> timeTable[]=new TreeMap[7];
         init(timeTable);
        System.out.println("Lecture Duration : ");
        int duration=sc.nextInt();
        System.out.println("Gap Between Lectures : ");
        int gap=sc.nextInt();
        sc.nextLine();
        for(int i=1;i<=6;)
        {
            if(!holidays.contains(i))
            {
                System.out.println("School Start time (hh:mm PM/AM) :"+hm.get(i));
                String start=sc.nextLine();
                System.out.println("School End time (hh:mm PM/AM) :"+hm.get(i));
                String end=sc.nextLine();
                if(lessThan(start,end))
                {
                    continue;
                }
                System.out.println("Lunch Start time (hh:mm PM/AM) :"+hm.get(i));
                String startLunch=sc.nextLine();
                System.out.println("Lunch End time (hh:mm PM/AM) :"+hm.get(i));
                String endLunch=sc.nextLine();
                if(lessThan(startLunch,endLunch))
                {
                    continue;
                }
                String st=start;
                String en=addTime(st,45);
                while(true) {
                    if(greaterThan(startLunch,en)&&lessThan(endLunch,st))
                    {
                        timeTable[i].put(new Pair(startLunch,endLunch),"Lunch Time");
                        st=endLunch;
                        en=addTime(st,duration);
                    }
                    if(greaterThan(end,en))
                        break;
                    System.out.println("Subject Name for :" +st+" - "+en);
                    String sub=sc.nextLine();
                    if(!subjects.contains(sub))
                    {
                        System.out.println("Subject doesn't Exist..");
                        continue;
                    }
                    timeTable[i].put(new Pair(st,en),sub);
                    st=addTime(en,gap);
                    en=addTime(st,duration);


                }
                timeTable[i].put(new Pair(start,end),"Day End Time");
            }
            else
            {
                Thread.sleep(500);
                System.out.println(hm.get(i)+" is a Holiday");
            }

            i++;
        }
        showTimetable(timeTable,holidays,hm);
    }
    public static void showTimetable(TreeMap<Pair,String> timeTable[],HashSet<Integer> holidays,HashMap<Integer,String> hm)
    {
        System.out.println("*************************************************** Time Table ***************************************************");
        for(int i=1;i<=6;i++)
        {
            System.out.println("On "+hm.get(i)+" : ");
            if(holidays.contains(i))
            {
                System.out.println("It's a Holiday");
            }
            else {
                for(Map.Entry<Pair,String> entry:timeTable[i].entrySet())
                {
                    Pair key=entry.getKey();
                    String value=entry.getValue();
                    if(value.equals("Day End Time"))
                    {
                        System.out.println(value+" : "+key.end);
                        continue;
                    }
                    System.out.println(value+" => "+key.start+" - "+key.end);
                }
            }
            System.out.println("\n");
        }
    }
     public static void init(TreeMap<Pair,String> timeTable[])
     {
         for(int i=0;i<timeTable.length;i++)
         {
             timeTable[i]=new TreeMap<>(new Comparator<Pair>() {
                 @Override
                 public int compare(Pair o1, Pair o2) {
                     return 1;
                 }
             });
         }
     }
      public static boolean lessThanOrEqualTo(String from,String to)
      {
          String a[]=from.split("[ :]+");
          String b[]=to.split("[ :]+");
          boolean result;
          if(a[2].equals(b[2]))
          {
              if(a[0].equals("12")&&b[0].equals("1"))
                  return false;
              result= a[0].compareTo(b[0])==0? (a[1].compareTo(b[1])>=0?true:false): a[0].compareTo(b[0])<0?  false: true;
          }
          else if(b[2].equals("PM"))
          {
              result= false;
          }
          else
          {
              result= true;
          }
          return result;
      }
    public static boolean lessThan(String from,String to)
    {
        String a[]=from.split("[ :]+");
        String b[]=to.split("[ :]+");
        boolean result;
        if(a[2].equals(b[2]))
        {
            if(a[0].equals("12")&&b[0].equals("1"))
                return false;
            if(a[0].equals("1")&&b[0].equals("12"))
                return true;
            result= a[0].compareTo(b[0])==0? (a[1].compareTo(b[1])>0?true:false): a[0].compareTo(b[0])<0?  false: true;
        }
        else if(b[2].equals("PM"))
        {
            result= false;
        }
        else
        {
            result= true;
        }
        return result;
    }
      public static boolean greaterThan(String from, String to)
      {
          String a[]=from.split("[: ]+");
          String b[]=to.split("[ :]+");
          if(a[2].equals(b[2]))
          {
              if(a[0].equals("1")&&b[0].equals("12"))
                  return false;
              if(a[0].equals("12")&&b[0].equals("1"))
                  return true;
              boolean result= a[0].compareTo(b[0])==0? (a[1].compareTo(b[1])<0?true:false): a[0].compareTo(b[0])>0?  false: true;
              return result;
          }
          else if(a[2].equals("AM"))
          {
              return true;
          }
          else
          {
              return false;
          }
      }
      public static String addTime(String s,int time)
      {
         String a[]= s.split("[: ]+");
         int cur=Integer.parseInt(a[1]);
         cur+=time;
         int prev=Integer.parseInt(a[0]);
         String meridian=a[2];
         if(cur>=60)
         {
             cur%=60;
             prev++;
             if(prev==12)
                 meridian="PM";
             if(prev>12)
             {
                 prev=1;
                 meridian="PM";
             }
         }
         return prev+":"+cur+" "+meridian;
      }

}

class Pair
{
    String start,end;
    Pair(String s,String e)
    {
        start=s;
        end=e;
    }
}












































//
//class Heap
//{
//    int heap[];
//    int size=0,cap;
//    Heap(int size)
//    {
//        cap=size;
//        heap=new int[cap];
//    }
//    public void add(int ele)
//    {
//        if(size==cap)
//        {
//            throw new ArraySizeFull("Heap is full");
//        }
//        heap[size++]=ele;
//        heapifyUp(size-1);
//    }
//    public int remove()
//    {
//        if(size==0)
//        {
//            throw new ArraySizeFull("Heap is full");
//        }
//        int ret=heap[0];
//        heap[0]=heap[size-1];
//        size--;
//        heapifyDown(0);
//        return ret;
//    }
//    public void heapifyUp(int i)
//    {
//      int parent= (i-1)/2;
//      while(parent>=0&&heap[parent]<heap[i])
//      {
//          int t=heap[parent];
//          heap[parent]=heap[i];
//          heap[i]=t;
//          i=parent;
//          parent=(i-1)/2;
//      }
//    }
//    public void heapifyDown(int k)
//    {
//        int i=k;
//        while(2*i+1<size)
//        {
//            int maxIndex=2*i+1;
//            if(2*i+2<size)
//            {
//                if(heap[maxIndex]<heap[2*i+2])
//                {
//                    maxIndex++;
//                }
//            }
//            if(heap[maxIndex]>heap[i])
//            {
//                int t=heap[i];
//                heap[i]=heap[maxIndex];
//                heap[maxIndex]=t;
//                i=maxIndex;
//            }
//            else
//            {
//                break;
//            }
//        }
//    }
//
//}
//class ArraySizeFull extends RuntimeException
//{
//   ArraySizeFull(String s)
//   {
//        super(s);
//   }
//}
