import java.util.Scanner;

class Process {
	int prio, active, no;
}

public class BullyRing {

	static Process[] p = new Process[10];
	static Process[] q = new Process[10];

	static int ch, p_no, i = 0, j, max, co_ordinator, new_p, new_p_prio;

	static int[] alive = new int[10];
	static int[] high_prio = new int[10];

	static int high_prio_cnt, pn, high;

	public static void main(String[] args) {
		for (int i = 0; i < p.length; i++) {
			p[i] = new Process();
		}

		for (int i = 0; i < q.length; i++) {
			q[i] = new Process();
		}

		Scanner scan = new Scanner(System.in);

		System.out.println("\n1.Bully\n2.Ring\n3.exit");
		System.out.println("\nEnter the choice : ");
		ch = scan.nextInt();

		switch (ch) {
			case 1:
				System.out.println("\nEnter the No of processes : ");
				p_no = scan.nextInt();
				for (i = 0; i < p_no; i++) {
					System.out.println("\nEnter the priority of Process P" + i + " : ");
					p[i].prio = scan.nextInt();
					p[i].no = i;
					p[i].active = 1;
				}
				sort();
				display();
				System.out.println("\n\nProcess Co-ordinator is Process P" + p[0].no + " with priority " + p[0].prio +" ...");
				System.out.println("\n\nChoose Process want to communicate with Co-ordinator : ");
				new_p = scan.nextInt();
				for (i = 0; i < p_no; i++) {
					if (p[i].no == new_p)
					new_p_prio = p[i].prio;
				}
				System.out.println("\nIt's Priority is " + new_p_prio + " ");
				System.out.println("\n\nProcess P" + new_p + " sending request to Co-ordinator ...");
				System.out.println("\n\nProcess P" + new_p + " doesn't getting response from Co-ordinator before TimeOut...");

				System.out.println("\n\ni.e. Co-Ordinator has been crashed ...");
				p[0].active = 0;
				System.out.println("\n\nProcess P" + new_p + " Ininiates Election Algo ...");
				System.out.println("\n\nProcess P" + new_p + " sending election massages to : ");
				high_prio_cnt = 0;
				j = 0;
				for (i = 0; i < p_no; i++) {
					if (p[i].prio >= new_p_prio && p[i].active == 1 && p[i].no != new_p) {
						System.out.println(" P" + p[i].no + " ");
						high_prio_cnt++;
						high_prio[j++] = p[i].no;
					}
				}
				if (high_prio_cnt == 0) {
					System.out.println("\n\n No one is Alive ...");
					System.out.println("\n\n*** Process P" + new_p + " is new Co-ordinator ... ***");
				} else {
					System.out.println("\n\n Processes replied to Process P" + new_p + " : ");

					for (i = 0; i < high_prio_cnt; i++)
						System.out.println(" P" + high_prio[i] + " ");

					System.out.println("\n\n Now Processes ");

					for (i = 0; i < high_prio_cnt; i++)
						System.out.println(" P" + high_prio[i] + " ");

					System.out.println(" doing Election among them");
					System.out.println("\n\n New Co-ordinator is Process P" + high_prio[0] + " ");
					System.out.println("\n\n New Co-ordinator sending Co-ordinator msg to all other Processes ... ");
				}
				break;

			case 2:
				high = -999;
				System.out.println("\n\nEnter The No Of Process:");
				p_no = scan.nextInt();
				System.out.println("\n\nEnter The Priorities For: ");
				for (i = 0; i < p_no; i++) {
					System.out.println("\n\nPriority Of P" + i + " :");
					p[i].prio = scan.nextInt();
					if (p[i].prio > high) {
						pn = i;
						high = p[i].prio;
					}
					System.out.println("\n\nP" + i + " Is Sending Msg To p" + (i + 1) % p_no);
					System.out.println("\n P" + i + " -> P" + (i + 1) % p_no);
				}
				System.out.println("\n\nDeciding Co=ordinator....");
				System.out.println("\n\nThe p" + pn + " Is Co-ordinator with priority " + high + "....");
				break;

			case 3:
				System.exit(0);

		}
	}

	static void display() {

		System.out.println("\nP_ID\t Priority\tActive");

		for (i = 0; i < p_no; i++)
		System.out.printf("\n\n%d\t\t%d\t\t%d", p[i].no, p[i].prio, p[i].active);
	}

	static void sort() {
		int j;
		Process process = new Process();
		Process temp = new Process();
		for (i = 0; i < p_no; i++) {
			max = p[i].prio;
			for (j = i + 1; j < p_no; j++) {
				if (p[j].prio > p[i].prio) {
					temp = p[j];
					p[j] = p[i];
					p[i] = temp;
				}
			}
		}
	}
}