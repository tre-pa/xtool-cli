export class Report {
    id: number;
    title: string;
    subtitle?: string;
    createdAt: Date;
    lastUpdateAt?: Date;
    version: number;
    uuid?: string;
    sql: string;
    grid?: ReportGrid;
    gpdf?: string;
    gexcel?: string;
    category: string;
    mngtOnly?: boolean = true;
    printOnly?: boolean = false;
}

export class ReportGrid {
    columns?: ReportColumn[];
    properties: Map<string, any>;
}

export class ReportColumn {
    dataField: string;
    caption: string;
    dataType: string;
    javaType: string;
    properties: Map<string, any>;
}
