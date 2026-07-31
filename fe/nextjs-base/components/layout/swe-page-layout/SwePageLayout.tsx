"use client"

import React from 'react';
import { SweToolbar, ToolbarProps } from "@/components/layout/components/toolbar/SweToolbar";

interface ISwePageLayoutProps extends ToolbarProps, Omit<React.HTMLProps<HTMLDivElement>, 'title'> {
    children: React.ReactNode;
}

const SwePageLayout = ({
    title = "",
    rightSideActionButtons,
    numberOfPickedRecords = 0,
    onDeselect,
    onDelete,
    children
}: ISwePageLayoutProps) => {
    return (
        <div className="flex flex-col gap-3 px-4 py-3">
            <SweToolbar
                title={title}
                rightSideActionButtons={rightSideActionButtons}
                numberOfPickedRecords={numberOfPickedRecords}
                onDeselect={onDeselect}
                onDelete={onDelete}
            />

            <div className="main_content pt-3">
                {children}
            </div>
        </div>
    );
};

export default SwePageLayout;